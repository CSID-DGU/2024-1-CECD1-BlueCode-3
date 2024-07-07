package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.Quiz;
import com.bluecode.chatbot.domain.QuizLevel;
import com.bluecode.chatbot.domain.QuizType;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.QuizRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Test
    @DisplayName("초기 테스트 생성 로직")
    public void testGetRandomQuizzesByType() {

        int chapterNum = 4; // 챕터 4
        QuizType type = QuizType.NUM; // 객관식
        QuizLevel quizLevel = QuizLevel.HARD; // 중급자
        int count = 3;  // 선택할 퀴즈 수

        Curriculums chap4 = curriculumRepository.findByRootIdAndChapterNum(1L, chapterNum).get();

        List<Quiz> quizzes = quizService.getRandomQuizzesByTypeAndLevel(chap4, type, quizLevel, 2);

        Assertions.assertThat(quizzes.size()).isEqualTo(2);
    }
}