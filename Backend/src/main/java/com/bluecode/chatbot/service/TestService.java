package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.QuizRepository;
import com.bluecode.chatbot.repository.TestRepository;
import com.bluecode.chatbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final QuizService quizService;
    private final CurriculumRepository curriculumRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    private static final String TEST_SUBMIT = "TEST_SUBMIT";
    private static final String TEST_PASS = "TEST_PASS";
    private static final String TEST_FAIL = "TEST_FAIL";

    // 미션 처리를 위한 클래스
    private final ApplicationEventPublisher eventPublisher;

    // 초기 테스트 구성
    public TestResponseDto createInitTest(DataCallDto dto) throws Exception {
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

        if (!chapter.get().isTestable()) {
            throw new IllegalArgumentException("테스트를 진행하지 않는 챕터입니다.");
        }

        // 각 난이도별로 퀴즈를 랜덤하게 선택
        List<Quiz> hardQuizzes = quizService.getRandomQuizzesByTypeAndLevel(chapter.get(), QuizType.NUM, QuizLevel.HARD, 2);
        List<Quiz> normalQuizzes = quizService.getRandomQuizzesByTypeAndLevel(chapter.get(), QuizType.NUM, QuizLevel.NORMAL, 1);
        List<Quiz> easyQuizzes = quizService.getRandomQuizzesByTypeAndLevel(chapter.get(), QuizType.NUM, QuizLevel.EASY, 1);

        // HARD - NORMAL - EASY - HARD 순으로 문제셋 구성
        List<Quiz> testQuizzes = new ArrayList<>();
        testQuizzes.add(hardQuizzes.get(0));
        testQuizzes.add(normalQuizzes.get(0));
        testQuizzes.add(easyQuizzes.get(0));
        testQuizzes.add(hardQuizzes.get(1));

        List<Tests> tests = new ArrayList<>();

        // tests 테이블에 생성된 초기 테스트 현황 생성 및 저장
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

        TestResponseDto responseDto = new TestResponseDto();
        List<TestResponseElementDto> testElements = new ArrayList<>();

        for (int i = 0; i < tests.size(); i++) {

            TestResponseElementDto element = new TestResponseElementDto();
            element.setTestId(tests.get(i).getTestId());
            element.setQuizId(testQuizzes.get(i).getQuizId());
            element.setText(testQuizzes.get(i).getText());
            element.setLevel(testQuizzes.get(i).getLevel());
            element.setQuizType(testQuizzes.get(i).getQuizType());
            element.setQ1(testQuizzes.get(i).getQ1());
            element.setQ2(testQuizzes.get(i).getQ2());
            element.setQ3(testQuizzes.get(i).getQ3());
            element.setQ4(testQuizzes.get(i).getQ4());
            testElements.add(element);
        }

        responseDto.setTests(testElements);

        log.info("createInitTest response: {}", responseDto);
        return responseDto;
    }

    // 이해도 테스트 구성
    public TestResponseDto createNormalTest(DataCallDto dto) throws Exception {

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
        List<Quiz> hardQuizzes = quizService.getRandomQuizzesByLevel(chapter.get(), QuizLevel.HARD, 1);
        List<Quiz> normalQuizzes = quizService.getRandomQuizzesByLevel(chapter.get(), QuizLevel.NORMAL, 1);
        List<Quiz> easyQuizzes = quizService.getRandomQuizzesByLevel(chapter.get(), QuizLevel.EASY, 1);

        // EASY - NORMAL - HARD 순으로 문제셋 구성
        List<Quiz> testQuizzes = new ArrayList<>();
        testQuizzes.add(easyQuizzes.get(0));
        testQuizzes.add(normalQuizzes.get(0));
        testQuizzes.add(hardQuizzes.get(0));

        List<Tests> tests = new ArrayList<>();

        // tests 테이블에 생성된 초기 테스트 현황 생성 및 저장
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

        TestResponseDto responseDto = new TestResponseDto();
        List<TestResponseElementDto> testElements = new ArrayList<>();

        for (int i = 0; i < tests.size(); i++) {

            TestResponseElementDto element = new TestResponseElementDto();
            element.setTestId(tests.get(i).getTestId());
            element.setQuizId(testQuizzes.get(i).getQuizId());
            element.setText(testQuizzes.get(i).getText());
            element.setLevel(testQuizzes.get(i).getLevel());
            element.setQuizType(testQuizzes.get(i).getQuizType());
            element.setQ1(testQuizzes.get(i).getQ1());
            element.setQ2(testQuizzes.get(i).getQ2());
            element.setQ3(testQuizzes.get(i).getQ3());
            element.setQ4(testQuizzes.get(i).getQ4());
            testElements.add(element);
        }

        responseDto.setTests(testElements);

        log.info("createNormalTest response: {}", responseDto);
        return responseDto;
    }

    // 객관식 문제 답안 판정
    public TestAnswerResponseDto submitAnswerNum(TestAnswerCallDto dto) {
        log.info("submitAnswer with dto: {}", dto);

        Optional<Users> user = userRepository.findById(dto.getUserId());
        Optional<Tests> test = testRepository.findById(dto.getTestId());
        Optional<Quiz> quiz = quizRepository.findById(dto.getQuizId());

        if (quiz.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 퀴즈 id 입니다.");
        }

        if (user.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (test.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 테스트 id 입니다.");
        }

        // 사용자 답안 채점
        boolean passed = quiz.get().getAnswer().equals(dto.getAnswer());
        test.get().setPassed(passed);

        if (passed) {

            // test 정답 제출 미션 처리 로직
            eventPublisher.publishEvent(new UserActionEvent(this, user.get(), ServiceType.TEST, TEST_PASS));

        } else {

            // 오답 횟수 증가
            test.get().setWrongCount(test.get().getWrongCount() + 1);
            // test 오답 제출 미션 처리 로직
            eventPublisher.publishEvent(new UserActionEvent(this, user.get(), ServiceType.TEST, TEST_FAIL));
        }

        testRepository.save(test.get());

        TestAnswerResponseDto responseDto = new TestAnswerResponseDto();
        responseDto.setPassed(passed);

        // test 제출 관련 미션 처리 로직
        eventPublisher.publishEvent(new UserActionEvent(this, user.get(), ServiceType.TEST, TEST_SUBMIT));

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

        return "초기 테스트 완료";
    }
}