package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.QuizRepository;
import com.bluecode.chatbot.repository.TestRepository;
import com.bluecode.chatbot.repository.UserRepository;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final QuizService quizService;
    private final CurriculumRepository curriculumRepository;
    private final QuizRepository quizRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;
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
            testQuizzes.addAll(quizService.generateQuizzesFromGPT(chapter.get(), QuizType.CODE, QuizLevel.HARD));
            testQuizzes.addAll(quizService.generateQuizzesFromGPT(chapter.get(), QuizType.NUM, QuizLevel.NORMAL));
            testQuizzes.addAll(quizService.generateQuizzesFromGPT(chapter.get(), QuizType.NUM, QuizLevel.EASY));
            testQuizzes.addAll(quizService.generateQuizzesFromGPT(chapter.get(), QuizType.CODE, QuizLevel.HARD));
        } else {
            // 데이터베이스에서 문제를 가져옴
            List<Quiz> hardQuizzes = quizService.getRandomQuizzesByTypeAndLevel(chapter.get(), QuizType.CODE, QuizLevel.HARD, 2);
            List<Quiz> normalQuizzes = quizService.getRandomQuizzesByTypeAndLevel(chapter.get(), QuizType.NUM, QuizLevel.NORMAL, 1);
            List<Quiz> easyQuizzes = quizService.getRandomQuizzesByTypeAndLevel(chapter.get(), QuizType.NUM, QuizLevel.EASY, 1);

            testQuizzes.add(hardQuizzes.get(0));
            testQuizzes.add(normalQuizzes.get(0));
            testQuizzes.add(easyQuizzes.get(0));
            testQuizzes.add(hardQuizzes.get(1));
        }

        List<Tests> tests = new ArrayList<>();
        for (Quiz quiz : testQuizzes) {
            Quiz savedQuiz = quizRepository.save(quiz);

            Tests test = new Tests();
            test.setUser(user.get());
            test.setQuiz(savedQuiz); // 저장된 Quiz 객체를 참조
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
            for (QuizLevel level : QuizLevel.values()) {
                List<QuizType> allowedTypes = new ArrayList<>();
                if (level == QuizLevel.EASY || level == QuizLevel.NORMAL) {
                    allowedTypes.add(QuizType.NUM);
                    allowedTypes.add(QuizType.WORD);
                } else {
                    allowedTypes.add(QuizType.CODE);
                }

                // 난이도별 허용되는 문제 유형 중 랜덤으로 선택
                QuizType randomType = allowedTypes.get(new Random().nextInt(allowedTypes.size()));
                testQuizzes.addAll(quizService.generateQuizzesFromGPT(chapter.get(), randomType, level));
            }
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
            Quiz savedQuiz = quizRepository.save(quiz);

            Tests test = new Tests();
            test.setUser(user.get());
            test.setQuiz(savedQuiz); // 저장된 Quiz 객체를 참조
            test.setPassed(false);
            test.setTestType(TestType.INIT);
            test.setWrongCount(0);

            testRepository.save(test);
            tests.add(test);
        }

        return createTestResponseDto(tests, testQuizzes);
    }

    // 테스트 결과를 DTO로 변환하여 반환하는 메서드
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