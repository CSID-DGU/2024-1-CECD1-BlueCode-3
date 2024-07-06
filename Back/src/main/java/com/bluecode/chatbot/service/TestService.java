package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.QuizRepository;
import com.bluecode.chatbot.repository.TestRepository;
import com.bluecode.chatbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {

    private final QuizService quizService;
    private final TestRepository testRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    // 초기 테스트용 문제 구성
    public TestResponseDto forInitialTest(Long userId, int chapterNum) {
        logger.info("forInitialTest with userId: {}, chapterNum: {}", userId, chapterNum);

        // 각 난이도별로 퀴즈를 랜덤하게 선택
        List<Quiz> hardQuizzes = quizService.getRandomQuizzesByType(chapterNum, QuizType.NUM, 2);
        List<Quiz> normalQuizzes = quizService.getRandomQuizzesByType(chapterNum, QuizType.NUM, 1);
        List<Quiz> easyQuizzes = quizService.getRandomQuizzesByType(chapterNum, QuizType.NUM, 1);

        // HARD - NORMAL - EASY - HARD 순으로 문제셋 구성
        List<Quiz> testQuizzes = new ArrayList<>();
        testQuizzes.add(hardQuizzes.get(0));
        testQuizzes.add(normalQuizzes.get(0));
        testQuizzes.add(easyQuizzes.get(0));
        testQuizzes.add(hardQuizzes.get(1));

        TestResponseDto responseDto = new TestResponseDto();
        List<TestResponseElementDto> testElements = new ArrayList<>();

        // 선택한 퀴즈를 DTO 요소로 변환
        for (Quiz quiz : testQuizzes) {
            TestResponseElementDto element = new TestResponseElementDto();
            element.setQuizId(quiz.getQuizId());
            element.setText(quiz.getText());
            element.setLevel(quiz.getLevel());
            element.setQuizType(quiz.getQuizType());
            element.setQ1(quiz.getQ1());
            element.setQ2(quiz.getQ2());
            element.setQ3(quiz.getQ3());
            element.setQ4(quiz.getQ4());
            testElements.add(element);
        }

        responseDto.setTests(testElements);

        logger.info("forInitialTest response: {}", responseDto);
        return responseDto;
    }

    // 초기테스트용 답안 채점
    public TestAnswerResponseDto submitAnswer(TestAnswerCallDto dto) {
        logger.info("submitAnswer with dto: {}", dto);

        Optional<Quiz> quizOptional = quizRepository.findById(dto.getQuizId());
        if (quizOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid quiz ID: " + dto.getQuizId());
        }
        Quiz quiz = quizOptional.get();

        Users user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + dto.getUserId()));

        Tests test = new Tests();
        test.setUser(user);
        test.setQuiz(quiz);

        // 사용자 답안 채점
        boolean passed = quiz.getAnswer().equals(dto.getAnswer());
        test.setPassed(passed);

        if (!passed) {
            test.setWrongCount(test.getWrongCount() + 1);
        }

        testRepository.save(test);

        TestAnswerResponseDto responseDto = new TestAnswerResponseDto();
        responseDto.setPassed(passed);

        logger.info("submitAnswer response: {}", responseDto);
        return responseDto;
    }
}