package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Quiz;
import com.bluecode.chatbot.domain.QuizType;
import com.bluecode.chatbot.repository.QuizRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class QuizServiceTest {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizService quizService;

    private static final Logger logger = LoggerFactory.getLogger(QuizServiceTest.class);

    @Test
    public void testGetRandomQuizzesByType() {
        logger.info("testGetRandomQuizzesByType 테스트 시작");

        int chapterNum = 1; // 챕터 1
        QuizType type = QuizType.NUM; // 객관식
        int count = 3;  // 선택할 퀴즈 수

        List<Quiz> quizzes = quizService.getRandomQuizzesByType(chapterNum, type, count);

        assertFalse(quizzes.isEmpty(), "퀴즈 리스트가 비어있습니다.");
        quizzes.forEach(quiz -> {
            logger.info("Selected quiz: {}", quiz);
            assertFalse(quiz.getQuizType() != type, "퀴즈 유형이 다릅니다.");
        });
    }
}