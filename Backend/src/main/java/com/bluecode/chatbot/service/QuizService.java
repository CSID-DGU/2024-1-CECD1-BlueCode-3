package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;
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
        String example = exampleQuiz(level, type);
        if (!explanation.isEmpty() && !example.isEmpty()) {
            messages.add(Map.of("role", "user", "content", "- 문제 구성 템플릿은 다음 설명을 참고: " + explanation
                    + "- 참고할 수 있는 문제 예시: " + example));
        }

        // ApiService를 사용해 GPT API 호출
        String response = apiService.sendPostRequest(messages, curriculumId);

        // 응답에서 퀴즈 문제 추출
        return apiService.extractContentFromResponse(response);
    }

    private String explanationQuiz(QuizLevel level, QuizType type) {
        if (level == QuizLevel.EASY && type == QuizType.NUM) {
            return "1. 객관식 문제로 4개의 보기를 낼 것\n" +
                    "2. 문제 내용은 'text:${문제 내용}$'이라고 명시하고 ${ }$ 안에 문제 내용을 쓸 것\n" +
                    "3. 문제 보기는 다음 형식으로 표기: 'q1:${보기1 내용}$, q2:${보기2 내용}$, q3:${보기3 내용}$, q4:${보기4 내용}$'\n" +
                    "4. 2-3줄의 이론과 관련한 내용으로 문제를 낼 것\n" +
                    "5. 정답은 마지막에 'ans:${정답 내용}$'으로 표기\n" +
                    "6. 마지막으로 생성한 응답 전체를 content:@{전체 내용}@으로 감쌀 것\n\n";
        } else if (level == QuizLevel.NORMAL && type == QuizType.WORD) {
            return "1. 주관식 문제로 간단한 예시 코드를 주고 그 실행 결과를 적도록 할 것\n" +
                    "2. 문제 내용은 'text:${문제 내용}$'이라고 명시하고 ${ }$ 안에 문제 내용을 쓸 것\n" +
                    "3. 답은 반드시 한 단어로 이루어지도록 할 것\n" +
                    "4. 2-3줄의 이론과 관련한 내용으로 문제를 낼 것" +
                    "5. 그 아래에 정답은 'ans:${정답 내용}$'으로 표기\n" +
                    "6. 마지막에 정답의 글자 수를 'count:${정답 글자 수}$'로 표기\n" +
                    "7. 마지막으로 생성한 응답 전체를 content:@{전체 내용}@으로 감쌀 것\n\n";
        } else if (level == QuizLevel.EASY && type == QuizType.WORD) {
            return "1. 주관식 문제로 '()' 안에 들어갈 알맞은 단어를 적도록 할 것\n" +
                    "2. 문제 내용은 'text:${문제 내용}$'이라고 명시하고 ${ }$ 안에 문제 내용을 쓸 것\n" +
                    "3. 문제는 2-3줄의 이론 내용으로 구성되어야 할 것\n" +
                    "4. 정답은 반드시 한 단어로 이루어지도록 할 것\n" +
                    "5. 그 아래에 정답은 'ans:${정답 내용}$'으로 표기\n" +
                    "6. 마지막에 정답의 글자 수를 'count:${정답 글자 수}$'로 표기\n" +
                    "7. 마지막으로 생성한 응답 전체를 content:@{전체 내용}@으로 감쌀 것\n\n";
        } else if (level == QuizLevel.NORMAL && type == QuizType.NUM) {
            return "1. 객관식 문제로 간단한 예시 코드를 주고, 그 실행 결과를 4개의 보기에서 고르도록 할 것\n" +
                    "2. 문제 내용은 'text:${문제 내용}$'이라고 명시하고 ${ }$ 안에 문제 내용을 쓸 것\n" +
                    "3. 문제 보기는 다음 형식으로 표기: 'q1:${보기1 내용}$, q2:${보기2 내용}$, q3:${보기3 내용}$, q4:${보기4 내용}$'\n" +
                    "4. 2-3줄의 이론과 관련한 내용으로 문제를 낼 것\n" +
                    "5. 정답은 마지막에 'ans:${정답 내용}$'으로 표기\n" +
                    "6. 마지막으로 생성한 응답 전체를 content:@{전체 내용}@으로 감쌀 것\n\n";
        } else if (level == QuizLevel.HARD && type == QuizType.CODE) {
            return "1. 심화 코딩 테스트 문제로 문제 제목, 문제 설명, 입력 내용, 출력 내용, 예제 입력, 예제 출력으로 내용을 구성\n" +
                    "2. 문제 내용은 'text:${문제 내용}$'이라고 명시하고 ${ }$ 안에 문제 내용을 쓸 것\n" +
                    "3. 문제 내용 아래에 내용에서 이미 언급한 예제 입력과 출력을 다음 형식으로 한 번 더 표기: " +
                    "'예제 입력1:${입력1 내용}$, 예제 출력1:${출력1 내용}$, 예제 입력2:${입력2 내용}$, 예제 출력2:${출력2 내용}$'\n" +
                    "4. 마지막으로 생성한 응답 전체를 content:@{전체 내용}@으로 감쌀 것\n\n";
        } else {
            return ""; // 해당하지 않는 경우 빈 문자열 반환
        }
    }

    // 예시 문제를 반환
    private String exampleQuiz(QuizLevel level, QuizType type) {
        if (level == QuizLevel.EASY && type == QuizType.NUM) {
            return "content:@{text:${다음 설명에 해당하는 자료형은 무엇일까요?\n" +
                    "\n" +
                    "- 순서가 보장되고, 중복되는 값을 허용하며, 요소를 추가, 삭제, 수정할 수 있는 자료형입니다.}$\n" +
                    "q1:${튜플}$, q2:${리스트}$, q3:${딕셔너리}$, q4:${집합}$\n" +
                    "ans:${리스트}$}@\n";
        } else if (level == QuizLevel.NORMAL && type == QuizType.WORD) {
            return "content:@{text:${```\n" +
                    "x = 7\n" +
                    "y = 3\n" +
                    "result = x // y + x % y\n" +
                    "print(result)\n" +
                    "```\n" +
                    "\n" +
                    "위 파이썬 코드의 실행 결과를 적으시오.}$\n" +
                    "ans:${7}$\n" +
                    "count:${1}$}@\n";
        } else if (level == QuizLevel.EASY && type == QuizType.WORD) {
            return "content:@{text:${다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n" +
                    "\n" +
                    "- 파이썬에서 변수의 자료형을 확인하기 위해 사용하는 함수는 (     )이다. 이 함수는 변수나 값을 입력받아 그 자료형을 반환한다.}$\n" +
                    "ans:${type}$\n" +
                    "count:${4}$}@\n";
        } else if (level == QuizLevel.NORMAL && type == QuizType.NUM) {
            return "content:@{text:{```\n" +
                    "x = 10\n" +
                    "y = 3\n" +
                    "print(x // y)\n" +
                    "```\n" +
                    "\n" +
                    "위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.}\n" +
                    "q1:${3.33333333333}$, q2:${3}$, q3:${1}$, q4:${오류 발생}$\n" +
                    "ans:${3}$}@\n";
        } else if (level == QuizLevel.HARD && type == QuizType.CODE) {
            return "content:@{text:{간단한 계산기\n" +
                    "\n" +
                    "사용자가 두 개의 정수를 입력하면, 다음의 연산을 수행하는 간단한 계산기 프로그램을 작성하시오.\n" +
                    "\n" +
                    "1. 두 정수의 합\n" +
                    "2. 두 정수의 차\n" +
                    "3. 두 정수의 곱\n" +
                    "4. 두 정수의 몫과 나머지\n" +
                    "\n" +
                    "입력:\n" +
                    "- 두 개의 정수를 각각 한 줄에 입력받습니다.\n" +
                    "\n" +
                    "출력:\n" +
                    "- 입력된 두 정수의 합, 차, 곱, 몫과 나머지를 순서대로 출력합니다.\n" +
                    "\n" +
                    "예제 입력 1:\n" +
                    "```\n" +
                    "10\n" +
                    "3\n" +
                    "```\n" +
                    "\n" +
                    "예제 출력 1:\n" +
                    "```\n" +
                    "합: 13\n" +
                    "차: 7\n" +
                    "곱: 30\n" +
                    "몫: 3, 나머지: 1\n" +
                    "```}\n" +
                    "예제 입력1:${10\n" +
                    "3}$, 예제 출력1:${합: 13\n" +
                    "차: 7\n" +
                    "곱: 30\n" +
                    "몫: 3, 나머지: 1}$}@\n";
        } else {
            return ""; // 해당하지 않는 경우 빈 문자열 반환
        }
    }
}