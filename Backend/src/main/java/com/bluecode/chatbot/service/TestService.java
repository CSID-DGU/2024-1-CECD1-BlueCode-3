package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.QuizRepository;
import com.bluecode.chatbot.repository.TestRepository;
import com.bluecode.chatbot.repository.UserRepository;
import com.bluecode.chatbot.repository.QuizCaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final QuizService quizService;
    private final CurriculumRepository curriculumRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuizCaseRepository quizCaseRepository;  // QuizCaseRepository 추가
    private final RestTemplate restTemplate;

    private final Random random = new Random();

    // 미션 처리를 위한 클래스
    private final ApplicationEventPublisher eventPublisher;

    @Value("${flask.url}")
    private String url;

    // 초기 테스트 구성
    public TestResponseDto createInitTest(DataCallDto dto) {
        log.info("createInitTest with userId: {}, curriculumId: {}", dto.getUserId(), dto.getCurriculumId());

        Optional<Users> user = userRepository.findByUserId(dto.getUserId());
        Optional<Curriculums> chapter = curriculumRepository.findById(dto.getCurriculumId());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (user.get().isInitTest()) {
            throw new IllegalArgumentException("이미 초기 테스트를 진행하였습니다.");
        }

        if (chapter.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 커리큘럼 id 입니다.");
        }

        if (chapter.get().isLeafNode() || chapter.get().isRootNode()) {
            throw new IllegalArgumentException("챕터가 아닙니다.");
        }

        if (!chapter.get().isTestable()) {
            throw new IllegalArgumentException("테스트를 진행하지 않는 챕터입니다.");
        }

        // 각 난이도별로 퀴즈를 랜덤하게 선택
        List<Quiz> testQuizzes = new ArrayList<>();

        // 랜덤으로 기존 데이터베이스 문제를 가져올지 또는 GPT API로 생성할지 결정
        boolean useGPT = random.nextBoolean();

        if (useGPT) {
            // GPT API를 사용해 문제를 생성
            testQuizzes.addAll(generateQuizzesFromGPT(chapter.get(), QuizType.NUM, QuizLevel.HARD));
            testQuizzes.addAll(generateQuizzesFromGPT(chapter.get(), QuizType.NUM, QuizLevel.NORMAL));
            testQuizzes.addAll(generateQuizzesFromGPT(chapter.get(), QuizType.NUM, QuizLevel.EASY));
            testQuizzes.addAll(generateQuizzesFromGPT(chapter.get(), QuizType.NUM, QuizLevel.HARD));
        } else {
            // 데이터베이스에서 문제를 가져옴
            List<Quiz> hardQuizzes = quizService.getRandomQuizzesByTypeAndLevel(chapter.get(), QuizType.NUM, QuizLevel.HARD, 2);
            List<Quiz> normalQuizzes = quizService.getRandomQuizzesByTypeAndLevel(chapter.get(), QuizType.NUM, QuizLevel.NORMAL, 1);
            List<Quiz> easyQuizzes = quizService.getRandomQuizzesByTypeAndLevel(chapter.get(), QuizType.NUM, QuizLevel.EASY, 1);

            testQuizzes.add(hardQuizzes.get(0));
            testQuizzes.add(normalQuizzes.get(0));
            testQuizzes.add(easyQuizzes.get(0));
            testQuizzes.add(hardQuizzes.get(1));
        }

        List<Tests> tests = new ArrayList<>();
        for (Quiz quiz : testQuizzes) {
            Tests test = new Tests();
            test.setUser(user.get());
            test.setQuiz(quiz);
            test.setPassed(false);
            test.setTestType(TestType.INIT);
            test.setWrongCount(0);

            testRepository.save(test);
            tests.add(test);
        }

        return createTestResponseDto(tests, testQuizzes);
    }

    // 이해도 테스트 구성
    public TestResponseDto createNormalTest(DataCallDto dto) {

        Optional<Users> user = userRepository.findByUserId(dto.getUserId());
        Optional<Curriculums> chapter = curriculumRepository.findById(dto.getCurriculumId());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (chapter.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 커리큘럼 id 입니다.");
        }

        if (!chapter.get().isTestable()) {
            throw new IllegalArgumentException("테스트를 진행하지 않는 챕터입니다.");
        }

        // 각 난이도별로 퀴즈를 랜덤하게 선택
        List<Quiz> testQuizzes = new ArrayList<>();

        // 랜덤으로 기존 데이터베이스 문제를 가져올지 또는 GPT API로 생성할지 결정
        boolean useGPT = random.nextBoolean();

        if (useGPT) {
            // GPT API를 사용해 문제를 생성
            testQuizzes.addAll(generateQuizzesFromGPT(chapter.get(), QuizType.NUM, QuizLevel.EASY));
            testQuizzes.addAll(generateQuizzesFromGPT(chapter.get(), QuizType.NUM, QuizLevel.NORMAL));
            testQuizzes.addAll(generateQuizzesFromGPT(chapter.get(), QuizType.NUM, QuizLevel.HARD));
        } else {
            // 데이터베이스에서 문제를 가져옴
            List<Quiz> hardQuizzes = quizService.getRandomQuizzesByLevel(chapter.get(), QuizLevel.HARD, 1);
            List<Quiz> normalQuizzes = quizService.getRandomQuizzesByLevel(chapter.get(), QuizLevel.NORMAL, 1);
            List<Quiz> easyQuizzes = quizService.getRandomQuizzesByLevel(chapter.get(), QuizLevel.EASY, 1);

            testQuizzes.add(easyQuizzes.get(0));
            testQuizzes.add(normalQuizzes.get(0));
            testQuizzes.add(hardQuizzes.get(0));
        }

        List<Tests> tests = new ArrayList<>();
        for (Quiz quiz : testQuizzes) {
            Tests test = new Tests();
            test.setUser(user.get());
            test.setQuiz(quiz);
            test.setPassed(false);
            test.setTestType(TestType.NORMAL);
            test.setWrongCount(0);

            testRepository.save(test);
            tests.add(test);
        }

        return createTestResponseDto(tests, testQuizzes);
    }

    // gpt api를 사용해 문제 생성 요청
    private List<Quiz> generateQuizzesFromGPT(Curriculums chapter, QuizType type, QuizLevel level) {
        String gptResponse = quizService.createQuizFromGPT(chapter.getCurriculumId(), type, level);
        log.info("GPT Response: {}", gptResponse); // gpt 응답 원문 확인 로그
        return parseGeneratedQuiz(gptResponse, chapter, type, level);
    }

    // gpt api 응답에서 문제를 원하는 양식으로 파싱
    private List<Quiz> parseGeneratedQuiz(String gptResponse, Curriculums curriculum, QuizType type, QuizLevel level) {
        List<Quiz> quizzes = new ArrayList<>();
        String[] quizSections = gptResponse.split("content:@\\{|}@");
        Map<Long, List<QuizCase>> quizCaseMap = new HashMap<>(); // Quiz ID를 키로 QuizCase 리스트 저장

        for (String section : quizSections) {
            if (section.isBlank()) continue;

            Quiz quiz = new Quiz();
            quiz.setCurriculum(curriculum);
            quiz.setLevel(level);

            // 정규 표현식을 사용하여 문제 정보 추출
            Pattern textPattern = Pattern.compile("text:\\$\\{(.*?)\\}$");
            Matcher textMatcher = textPattern.matcher(section);
            if (textMatcher.find()) {
                quiz.setText(textMatcher.group(1));
            }

            // 문제 유형에 따라 추가 정보 추출
            switch (type) {
                case NUM:
                    quiz.setAnswer(extractContent(section, "ans"));
                    quiz.setQ1(extractContent(section, "q1"));
                    quiz.setQ2(extractContent(section, "q2"));
                    quiz.setQ3(extractContent(section, "q3"));
                    quiz.setQ4(extractContent(section, "q4"));
                    break;
                case WORD:
                    quiz.setAnswer(extractContent(section, "ans"));
                    // 정답의 글자 수를 추출하여 저장
                    Matcher wordCountMatcher = Pattern.compile("count:\\$\\{(.*?)\\}$").matcher(section);
                    if (wordCountMatcher.find()) {
                        quiz.setWordCount(Integer.parseInt(wordCountMatcher.group(1)));
                    }
                    break;
                case CODE:
                    List<QuizCase> cases = new ArrayList<>();
                    for (int i = 1; i <= 10; i++) {
                        String input = extractContent(section, "예제 입력" + i);
                        String output = extractContent(section, "예제 출력" + i);
                        if (input != null && output != null) {
                            cases.add(QuizCase.createQuizCase(quiz, input, output));
                        }
                    }
                    quizCaseMap.put(quiz.getQuizId(), cases); // Quiz ID를 키로 QuizCase 리스트 저장

                    quizzes.add(quizRepository.save(quiz));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported quiz type: " + type);
            }

            quizzes.add(quizRepository.save(quiz));
        }

        return quizzes;
    }

    private String extractContent(String section, String keyword) {
        Matcher matcher = Pattern.compile(String.format("%s:\\$\\{(.*?)\\}$", keyword)).matcher(section);
        return matcher.find() ? matcher.group(1) : "";
    }

    private List<QuizCase> extractQuizCases(String section, Quiz quiz) {
        List<QuizCase> cases = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String input = extractContent(section, "예제 입력" + i);
            String output = extractContent(section, "예제 출력" + i);
            if (input != null && output != null) {
                cases.add(QuizCase.createQuizCase(quiz, input, output));
            }
        }
        return cases;
    }

    private TestResponseDto createTestResponseDto(List<Tests> tests, List<Quiz> quizzes) {
        List<TestResponseElementDto> testDtos = new ArrayList<>();

        for (int i = 0; i < tests.size(); i++) {
            Tests test = tests.get(i);
            Quiz quiz = quizzes.get(i);

            TestResponseElementDto dto = new TestResponseElementDto();
            // Tests 객체에서 값 가져오기
            dto.setTestId(test.getTestId());
            // Quiz 객체에서 값 가져오기
            dto.setQuizId(quiz.getQuizId());
            dto.setText(quiz.getText());
            dto.setLevel(quiz.getLevel());
            dto.setQuizType(quiz.getQuizType());
            dto.setQ1(quiz.getQ1());
            dto.setQ2(quiz.getQ2());
            dto.setQ3(quiz.getQ3());
            dto.setQ4(quiz.getQ4());
            dto.setWordCount(quiz.getWordCount());

            testDtos.add(dto);
        }

        TestResponseDto dto = new TestResponseDto();
        dto.setTests(testDtos);

        return dto;
    }


    // 객관식 문제 답안 판정
    public TestAnswerResponseDto submitAnswerNum(TestAnswerCallDto dto) {
        log.info("submitAnswer with dto: {}", dto);

        Optional<Users> usersOptional = userRepository.findById(dto.getUserId());
        Optional<Tests> testsOptional = testRepository.findById(dto.getTestId());

        if (usersOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (testsOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 테스트 id 입니다.");
        }

        Users user = usersOptional.get();
        Tests test = testsOptional.get();

        if (test.getQuiz().getQuizType() != QuizType.NUM) {
            throw new IllegalArgumentException("해당 문제는 객관식이 아닙니다. quizType: " + test.getQuiz().getQuizType());
        }

        // 사용자 답안 채점
        boolean passed = test.getQuiz().getAnswer().equals(dto.getAnswer());
        if (test.getWrongCount() == 0) {
            test.setPassed(true);
        }

        // 테스트 채점 및 미션 처리
        publishPassMission(passed, user, test);
        testRepository.save(test);

        TestAnswerResponseDto responseDto = new TestAnswerResponseDto();
        responseDto.setPassed(passed);

        publishMission(user, test);

        log.info("submitAnswer response: {}", responseDto);
        return responseDto;
    }


    // 단답형 문제 답안 판정
    public TestAnswerResponseDto submitAnswerWord(TestAnswerCallDto dto) {
        log.info("submitAnswer with dto: {}", dto);

        Optional<Users> usersOptional = userRepository.findById(dto.getUserId());
        Optional<Tests> testsOptional = testRepository.findById(dto.getTestId());


        if (usersOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (testsOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 테스트 id 입니다.");
        }

        Users user = usersOptional.get();
        Tests test = testsOptional.get();

        if (test.getQuiz().getQuizType() != QuizType.WORD) {
            throw new IllegalArgumentException("해당 문제는 단답형이 아닙니다. quizType: " + test.getQuiz().getQuizType());
        }

        // 사용자 답안 채점
        boolean passed = test.getQuiz().getAnswer().equals(dto.getAnswer());
        if (test.getWrongCount() == 0) {
            test.setPassed(true);
        }

        // 테스트 채점 및 미션 처리
        publishPassMission(passed, user, test);
        testRepository.save(test);

        TestAnswerResponseDto responseDto = new TestAnswerResponseDto();
        responseDto.setPassed(passed);

        // test 제출 관련 미션 처리 로직
        publishMission(user, test);

        log.info("submitAnswer response: {}", responseDto);
        return responseDto;
    }

    // 단답형 문제 답안 판정
    public TestAnswerResponseDto submitAnswerCode(TestAnswerCallDto dto) {
        log.info("submitAnswer with dto: {}", dto);

        Optional<Users> usersOptional = userRepository.findById(dto.getUserId());
        Optional<Tests> testsOptional = testRepository.findById(dto.getTestId());


        if (usersOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (testsOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 테스트 id 입니다.");
        }

        Users user = usersOptional.get();
        Tests test = testsOptional.get();

        if (test.getQuiz().getQuizType() != QuizType.CODE) {
            throw new IllegalArgumentException("해당 문제는 코드작성형이 아닙니다. quizType: " + test.getQuiz().getQuizType());
        }

        // 데이터 변환
        CodeRequestDto codeRequestDto = new CodeRequestDto();
        codeRequestDto.setUserId(dto.getUserId());
        codeRequestDto.setCode(dto.getAnswer());
        codeRequestDto.setLanguage(test.getQuiz().getCurriculum().getLangType().toString()); // 고정된 값 또는 설정에 따라 변동 가능
        codeRequestDto.setQuizId(test.getQuiz().getQuizId());

        // 코드실행서버 서버 API URL
        String postUrl = url + "/code/run_code";

        // 요청 헤더와 바디 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        HttpEntity<CodeRequestDto> requestEntity = new HttpEntity<>(codeRequestDto, headers);

        // API 호출
        ResponseEntity<CodeResponseDto> responseEntity = restTemplate.exchange(
                postUrl,
                HttpMethod.POST,
                requestEntity,
                CodeResponseDto.class
        );

        // 결과 처리
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("코드 실행 서버 접근 불가");
        }

        CodeResponseDto responseBody = responseEntity.getBody();
        boolean passed = "정답".equals(responseBody.getResult());

        // 사용자 답안 채점
        if (test.getWrongCount() == 0) {
            test.setPassed(true);
        }

        // 테스트 채점 및 미션 처리
        publishPassMission(passed, user, test);
        testRepository.save(test);

        TestAnswerResponseDto responseDto = new TestAnswerResponseDto();
        responseDto.setPassed(passed);
        // 코드작성형 실행 결과 태그 설정
        responseDto.setResult(responseBody.getResult());

        // test 제출 관련 미션 처리 로직
        publishMission(user, test);

        log.info("submitAnswer response: {}", responseDto);
        return responseDto;
    }

    // 초기 테스트 완료 판정
    public String completeInitTest(Long userId) {

        Optional<Users> user = userRepository.findByUserId(userId);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (user.get().isInitTest()) {
            throw new IllegalArgumentException("이미 초기 테스트 완료 처리 되었습니다.");
        }

        // 초기 테스트 완료 표시
        user.get().setInitTest(true);
        userRepository.save(user.get());

        // test 초기 테스트 완료 관련 미션 처리 로직
        eventPublisher.publishEvent(new UserActionEvent(this, user.get(), ServiceType.TEST, MissionConst.TEST_INIT_COMPLETE));

        return "초기 테스트 완료";
    }


    // 공통 제출 미션 처리
    private void publishMission(Users user, Tests test) {
        // test 제출 관련 미션 처리 로직
        eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.TEST, MissionConst.TEST_SUBMIT));

        // 난이도별 test 제출 미션 처리 로직
        if (test.getQuiz().getLevel().equals(QuizLevel.EASY)) {
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.TEST, MissionConst.TEST_EASY_SUBMIT));
        } else if (test.getQuiz().getLevel().equals(QuizLevel.NORMAL)) {
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.TEST, MissionConst.TEST_NORMAL_SUBMIT));
        } else if (test.getQuiz().getLevel().equals(QuizLevel.HARD)) {
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.TEST, MissionConst.TEST_HARD_SUBMIT));
        }
    }

    // 공통 채점 미션 처리
    private void publishPassMission(boolean passed, Users user, Tests test) {
        if (passed) {
            // test 정답 제출 미션 처리 로직
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.TEST, MissionConst.TEST_PASS));

            if (test.getQuiz().getLevel().equals(QuizLevel.EASY)) {
                eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.TEST, MissionConst.TEST_EASY_PASS));
            } else if (test.getQuiz().getLevel().equals(QuizLevel.NORMAL)) {
                eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.TEST, MissionConst.TEST_NORMAL_PASS));
            } else if (test.getQuiz().getLevel().equals(QuizLevel.HARD)) {
                eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.TEST, MissionConst.TEST_HARD_PASS));
            }
        } else {
            // 오답 횟수 증가
            test.setWrongCount(test.getWrongCount() + 1);
            // test 오답 제출 미션 처리 로직
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.TEST, MissionConst.TEST_FAIL));
        }
    }
}