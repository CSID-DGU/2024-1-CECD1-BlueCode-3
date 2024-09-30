package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.QuizRepository;
import com.bluecode.chatbot.repository.QuizCaseRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ParsingException;
import org.springframework.stereotype.Service;

import java.security.Provider;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizCaseRepository quizCaseRepository;
    private final ApiService apiService;
    private final CurriculumRepository curriculumRepository;

    // 특정 커리큘럼, 특정 퀴즈 레벨, 특정 유형의 n개의 문제를 뽑는 메서드
    public List<Quiz> getRandomQuizzesByTypeAndLevel(Curriculums curriculums, QuizType type, QuizLevel level, int count) {
        log.info("getRandomQuizzesByType curriculum: {}, type: {}, level: {}, count: {}", curriculums.getCurriculumName(), type, level, count);

        // 루트 커리큘럼을 대상으로 호출 시 Exception 발생
        if (curriculums.getChapterNum() == 0) {
            throw new IllegalArgumentException("루트 커리큘럼은 유효하지 않습니다.");
        }

        // 주어진 챕터와 유형에 맞는 모든 퀴즈를 담은 리스트
        List<Quiz> quizzes = quizRepository.findAllByCurriculumIdAndQuizTypeAndLevel(curriculums.getCurriculumId(), type, level);

        // 퀴즈가 존재하지 않을 경우
        if (quizzes.isEmpty()) {
            log.warn("No quizzes found for curriculum: {}, type: {}, level: {}", curriculums.getCurriculumName(), type, level);
            throw new IllegalArgumentException("대상 커리큘럼에 해당하는 문제가 존재하지 않습니다.");
        }

        // 퀴즈 리스트를 랜덤하게 섞음
        Collections.shuffle(quizzes, new Random());

        // count로 정한 수만큼 문제를 선택하여 담은 리스트
        List<Quiz> selectedQuizzes = quizzes.stream()
                .limit(count)
                .toList();

        log.info("Selected quizzes: {}", selectedQuizzes);
        return selectedQuizzes;
    }

    // 특정 커리큘럼, 특정 퀴즈 레벨의 n개의 문제를 뽑는 메서드
    public List<Quiz> getRandomQuizzesByLevel(Curriculums curriculums, QuizLevel quizLevel, int count) {

        // 루트 커리큘럼을 대상으로 호출 시 Exception 발생
        if (curriculums.getChapterNum() == 0) {
            throw new IllegalArgumentException("루트 커리큘럼은 유효하지 않습니다.");
        }

        List<Quiz> quizzes = quizRepository.findAllByCurriculumIdAndLevel(curriculums.getCurriculumId(), quizLevel);
        log.info("getRandomQuizzesByType curriculum: {}, level: {}, count: {}", curriculums.getCurriculumName(), quizLevel, count);

        // 퀴즈가 존재하지 않을 경우 Exception 발생
        if (quizzes.isEmpty()) {
            throw new IllegalArgumentException("대상 커리큘럼에 해당하는 문제가 존재하지 않습니다.");
        }

        // 퀴즈 리스트를 랜덤하게 섞음
        Collections.shuffle(quizzes, new Random());

        // count로 정한 수만큼 문제를 선택하여 담은 리스트

        return quizzes.stream()
                .limit(count)
                .toList();
    }

    // GPT API를 사용하여 테스트 문제를 생성
    public String createQuizFromGPT(Long curriculumId, QuizType type, QuizLevel level) {
        Curriculums currentCurriculum = curriculumRepository.findById(curriculumId).orElse(null);

        // 루트 커리큘럼 이름 조회
        String rootChapName = currentCurriculum != null ? currentCurriculum.getRoot().getCurriculumName() : "알 수 없음";
        log.info("rootChapName: {}", rootChapName);

        // 현재 챕터 이름 조회
        String chapName = currentCurriculum != null ? currentCurriculum.getCurriculumName() : "알 수 없음";
        log.info("chapName: {}", chapName);

        // 서브 챕터 리스트 조회
        List<Curriculums> subChapters = Objects.requireNonNull(currentCurriculum).getChildren().stream().toList();

        // 서브 챕터 리스트에서 이름만 추출하여 문자열로 변환
        List<String> subChapterNames = subChapters.stream().map(Curriculums::getCurriculumName).toList();

        String subChapterNamesString = String.join(", ", subChapterNames);

        // GPT API에 전달할 메시지 생성
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", rootChapName + "에서 "
                + chapName + " 챕터의 '" + subChapterNamesString + "'에 관한 문제 1개를 다음 내용을 참고하여 생성하시오.\n\n"));

        // 예시 문제를 추가하여 메시지에 포함
        String explanation = explanationQuiz(level, type);
        String template = quizTemplate();
        String example = exampleQuiz(level, type);
        if (!explanation.isEmpty() && !example.isEmpty()) {
            messages.add(Map.of("role", "user", "content", "- 문제 구성 법칙은 다음 설명을 참고: " + explanation
                    + "\n- JSON 형식의 문제 템플릿: " + template
                    + "\n- 참고할 수 있는 문제 형태 예시: " + example));
        }

        // ApiService를 사용해 GPT API 호출
        String response = apiService.sendPostRequest(messages, curriculumId);

        // 응답에서 퀴즈 문제 추출
        return apiService.extractContentFromResponse(response);
    }

    private String explanationQuiz(QuizLevel level, QuizType type) {
        if (level == QuizLevel.EASY && type == QuizType.NUM) {
            return "1. This is a multiple-choice question with quizLevel set to 'EASY' and quizType set to 'NUM'.\n" +
                    "2. The entire content of the question should be placed in 'text'.\n" +
                    "3. Each option should be placed in 'q1', 'q2', 'q3', and 'q4'.\n" +
                    "4. The question should be related to theoretical content and should be 2-3 sentences long.\n" +
                    "5. Instead of the option number, the correct answer should be placed in 'ans'.\n" +
                    "6. All other values should be set to null.\n" +
                    "7. 'wordCount' should be set to 0.\n" +
                    "8. All above contents should be generated in Korean.\n\n";
        } else if (level == QuizLevel.NORMAL && type == QuizType.WORD) {
            return "1. This is a short-answer question with quizLevel set to 'NORMAL' and quizType set to 'WORD'.\n" +
                    "2. Present a simple code example and have the user predict the result.\n" +
                    "3. The entire question, including the example code, should be placed in 'text'.\n" +
                    "4. The correct answer should be placed in 'ans'.\n" +
                    "5. The answer should consist of a single word.\n" +
                    "6. The number of characters in the answer should be placed in 'wordCount'.\n" +
                    "7. All other values should be set to null.\n" +
                    "8. All above contents should be generated in Korean.\n\n";
        } else if (level == QuizLevel.EASY && type == QuizType.WORD) {
            return "1. This is a short-answer question with quizLevel set to 'EASY' and quizType set to 'WORD'.\n" +
                    "2. The entire content of the question should be placed in 'text'.\n" +
                    "3. Create a theoretical question of 2-3 sentences where the user has to guess the word that fits inside the parentheses '(  )'.\n" +
                    "4. The correct answer should be placed in 'ans'.\n" +
                    "5. The answer should consist of a single word.\n" +
                    "6. The number of characters in the answer should be placed in 'wordCount'.\n" +
                    "7. All other values should be set to null.\n" +
                    "8. All above contents should be generated in Korean.\n\n";
        } else if (level == QuizLevel.NORMAL && type == QuizType.NUM) {
            return "1. This is a multiple-choice question with quizLevel set to 'NORMAL' and quizType set to 'NUM'.\n" +
                    "2. Present a simple code example and have the user predict the result.\n" +
                    "3. The entire question, including the example code, should be placed in 'text'.\n" +
                    "4. Each option should be placed in 'q1', 'q2', 'q3', and 'q4'.\n" +
                    "5. Instead of the option number, the correct answer should be placed in 'ans'.\n" +
                    "6. All other values should be set to null.\n" +
                    "7. 'wordCount' should be set to 0.\n" +
                    "8. All above contents should be generated in Korean.\n\n";
        } else if (level == QuizLevel.HARD && type == QuizType.CODE) {
            return "1. This is an advanced coding test question with quizLevel set to 'HARD' and quizType set to 'CODE'.\n" +
                    "2. The question should consist of the problem title, problem description, input description, output description, and 2 to 5 example inputs and outputs.\n" +
                    "3. The entire content of the question should be placed in 'text'.\n" +
                    "4. Ensure that only the content inside 'text' is used, similar to reference questions.\n" +
                    "5. The example inputs and outputs in the question should be placed in 'text', but create **different** example inputs and outputs from 'text' for 'examples'. Each example input should be placed in 'input' and its corresponding output in 'output', and they should be paired as lists.\n" +
                    "6. All other values should be set to null.\n" +
                    "7. 'wordCount' should be set to 0.\n" +
                    "8. Do not include questions related to random values.\n" +
                    "9. Avoid file input/output-related questions.\n" +
                    "10. In input/output examples, include only the input and output values.\n" +
                    "11. All above contents should be generated in Korean.\n\n";
        } else {
            return ""; // 해당하지 않는 경우 빈 문자열 반환
        }
    }

    private String quizTemplate() {
        return "{\n" +
                "  \"text\": \"문제 내용\",\n" +
                "  \"level\": \"QuizLevel\",\n" +
                "  \"quizType\": \"QuizType\",\n" +
                "  \"q1\": \"보기1 내용\",\n" +
                "  \"q2\": \"보기2 내용\",\n" +
                "  \"q3\": \"보기3 내용\",\n" +
                "  \"q4\": \"보기4 내용\",\n" +
                "  \"examples\": [\n" +
                "    {\n" +
                "      \"input\": \"입력 예제 내용 1\",\n" +
                "      \"output\": \"출력 예제 내용 1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"input\": \"입력 예제 내용 2\",\n" +
                "      \"output\": \"출력 예제 내용 2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"input\": \"입력 예제 내용 3(존재할 경우 사용)\",\n" +
                "      \"output\": \"출력 예제 내용 3(존재할 경우 사용)\"\n" +
                "    }\n" +
                "    // 추가 입력/출력 예제 가능\n" +
                "  ],\n" +
                "  \"ans\": \"정답 내용\",\n" +
                "  \"wordCount\": 정답 글자 수\n" +
                "}\n";
    }

    // 예시 문제를 반환
    private String exampleQuiz(QuizLevel level, QuizType type) {
        if (level == QuizLevel.EASY && type == QuizType.NUM) {
            return "{\n" +
                    "\"text\": \"다음 설명에 해당하는 자료형은 무엇일까요?\\n\\n- 순서가 보장되고, " +
                    "중복되는 값을 허용하며, 요소를 추가, 삭제, 수정할 수 있는 자료형입니다.\",\n" +
                    "\"level\": \"EASY\",\n" +
                    "\"quizType\": \"NUM\",\n" +
                    "\"q1\": \"튜플\",\n" +
                    "\"q2\": \"리스트\",\n" +
                    "\"q3\": \"딕셔너리\",\n" +
                    "\"q4\": \"집합\",\n" +
                    "\"input\": null,\n" +
                    "\"output\": null,\n" +
                    "\"ans\": \"리스트\",\n" +
                    "\"wordCount\": 0\n" +
                    "}\n";
        } else if (level == QuizLevel.NORMAL && type == QuizType.WORD) {
            return "{\n" +
                    "\"text\": \"```\\nx = 7\\ny = 3\\nresult = x // y + x % y\\n" +
                    "print(result)\\n```\\n\\n위 파이썬 코드의 실행 결과를 적으시오.\",\n" +
                    "\"level\": \"NORMAL\",\n" +
                    "\"quizType\": \"WORD\",\n" +
                    "\"q1\": null,\n" +
                    "\"q2\": null,\n" +
                    "\"q3\": null,\n" +
                    "\"q4\": null,\n" +
                    "\"input\": null,\n" +
                    "\"output\": null,\n" +
                    "\"ans\": \"7\",\n" +
                    "\"wordCount\": 1\n" +
                    "}\n";
        } else if (level == QuizLevel.EASY && type == QuizType.WORD) {
            return "{\n" +
                    "\"text\": \"다음 괄호 안에 들어갈 알맞은 답을 적으세요.\\n\\n- 파이썬에서 변수의 자료형을 " +
                    "확인하기 위해 사용하는 함수는 (    )이다. 이 함수는 변수나 값을 입력받아 그 자료형을 반환한다.\",\n" +
                    "\"level\": \"EASY\",\n" +
                    "\"quizType\": \"WORD\",\n" +
                    "\"q1\": null,\n" +
                    "\"q2\": null,\n" +
                    "\"q3\": null,\n" +
                    "\"q4\": null,\n" +
                    "\"input\": null,\n" +
                    "\"output\": null,\n" +
                    "\"ans\": \"type\",\n" +
                    "\"wordCount\": 4\n" +
                    "}\n";
        } else if (level == QuizLevel.NORMAL && type == QuizType.NUM) {
            return "{\n" +
                    "\"text\": \"```\\nx = 10\\ny = 3\\nprint(x // y)\\n```\\n" +
                    "\\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.\",\n" +
                    "\"level\": \"NORMAL\",\n" +
                    "\"quizType\": \"NUM\",\n" +
                    "\"q1\": \"3.33333333333\",\n" +
                    "\"q2\": \"3\",\n" +
                    "\"q3\": \"1\",\n" +
                    "\"q4\": \"오류 발생\",\n" +
                    "\"input\": null,\n" +
                    "\"output\": null,\n" +
                    "\"ans\": \"3\",\n" +
                    "\"wordCount\": 0\n" +
                    "}\n";
        } else if (level == QuizLevel.HARD && type == QuizType.CODE) {
            return "{\n" +
                    "\"text\": \"점수에 따른 학점 계산기\\n\\n사용자가 입력한 점수에 따라 학점을 출력하는 프로그램을 작성하세요. " +
                    "점수는 0에서 100 사이의 정수로 입력됩니다. 다음 규칙에 따라 학점을 출력하세요.\\n\\n1. 90점 이상: A" +
                    "\\n2. 80점 이상 90점 미만: B\\n3. 70점 이상 80점 미만: C\\n4. 60점 이상 70점 미만: D\\n5. 60점 미만: F" +
                    "\\n6. 또한, 점수가 0 미만이거나 100을 초과하면 \\\"잘못된 점수입니다.\\\"를 출력\\n\\n입력 예제 1:\\n85\\n\\n" +
                    "출력 예제 1:\\nB\\n\\n입력 예제 2:\\n105\\n\\n출력 예제 2:\\n잘못된 점수입니다.\",\n" +
                    "\"level\": \"HARD\",\n" +
                    "\"quizType\": \"CODE\",\n" +
                    "\"q1\": null,\n" +
                    "\"q2\": null,\n" +
                    "\"q3\": null,\n" +
                    "\"q4\": null,\n" +
                    "\"examples\": [\n" +
                    "    {\n" +
                    "      \"input\": \"59\",\n" +
                    "      \"output\": \"F\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"input\": \"-10\",\n" +
                    "      \"output\": \"잘못된 점수입니다.\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "\"ans\": null,\n" +
                    "\"wordCount\": 0\n" +
                    "}\n";
        } else {
            return ""; // 해당하지 않는 경우 빈 문자열 반환
        }
    }

    // gpt api를 사용해 문제 생성 요청
    public List<Quiz> generateQuizzesFromGPT(Curriculums chapter, QuizType type, QuizLevel level) {
        String gptResponse = createQuizFromGPT(chapter.getCurriculumId(), type, level);

        // JSON 앞뒤 백틱(```) 제거
        if (gptResponse.startsWith("```")) {
            gptResponse = gptResponse.substring(3); // 앞쪽 백틱 제거
        }
        if (gptResponse.endsWith("```")) {
            gptResponse = gptResponse.substring(0, gptResponse.length() - 3); // 뒤쪽 백틱 제거
        }

        String root = chapter.getRoot().getCurriculumName(); // 루트 커리큘럼

        // 응답 json 필드에 있는 불필요한 이스케이프 문자 또는 형식 문구 제거
        gptResponse = gptResponse.replace("json", "").replace(root, "")
                .replaceAll("(?<=\\w):\\s*[\r\n\t]+", ": ");

        log.info("GPT Response: {}", gptResponse); // gpt 응답 원문 확인 로그

        return parseGeneratedQuiz(gptResponse, chapter, type, level);
    }

    // gpt api 응답 JSON에서 구성 요소 파싱
    private List<Quiz> parseGeneratedQuiz(String gptResponse, Curriculums curriculum, QuizType type, QuizLevel level) {
        List<Quiz> quizzes = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 알 수 없는 속성 허용
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true); // 알 수 없는 문자 허용

        try {
            JsonNode rootNode = objectMapper.readTree(gptResponse);

            // Quiz 객체 생성 및 설정
            Quiz quiz = new Quiz();
            quiz.setCurriculum(curriculum);
            quiz.setQuizType(QuizType.valueOf(rootNode.path("quizType").asText()));
            quiz.setLevel(QuizLevel.valueOf(rootNode.path("level").asText()));
            quiz.setText(rootNode.path("text").asText());

            // 문제 유형에 따라 추가 정보 설정
            if (quiz.getQuizType() == QuizType.NUM) {
                // Quiz 엔티티를 먼저 저장하여 quizId를 생성
                quiz = quizRepository.save(quiz);
                quiz.setQ1(rootNode.path("q1").asText(null));
                quiz.setQ2(rootNode.path("q2").asText(null));
                quiz.setQ3(rootNode.path("q3").asText(null));
                quiz.setQ4(rootNode.path("q4").asText(null));
                quiz.setAnswer(rootNode.path("ans").asText(null));
            } else if (quiz.getQuizType() == QuizType.CODE) {
                // Quiz 엔티티를 먼저 저장하여 quizId를 생성
                quiz = quizRepository.save(quiz);

                // examples 리스트에서 input-output 쌍을 추출하고 QuizCase에 저장
                JsonNode examplesNode = rootNode.path("examples");
                if (examplesNode.isArray()) {
                    for (JsonNode exampleNode : examplesNode) {
                        QuizCase quizCase = QuizCase.createQuizCase(
                                quiz,
                                exampleNode.path("input").asText(null),
                                exampleNode.path("output").asText(null)
                        );
                        quizCaseRepository.save(quizCase);
                    }
                }
            } else if (quiz.getQuizType() == QuizType.WORD) {
                // Quiz 엔티티를 먼저 저장하여 quizId를 생성
                quiz = quizRepository.save(quiz);
                quiz.setAnswer(rootNode.path("ans").asText(null));
                quiz.setWordCount(rootNode.path("wordCount").asInt(0));
            }

            quizzes.add(quiz);

        } catch (Exception e) {
            log.error("파싱 오류", e);
            throw new ParsingException("GPT 응답 파싱 중 오류 발생");
        }

        return quizzes;
    }
}