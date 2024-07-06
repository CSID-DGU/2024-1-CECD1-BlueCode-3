package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Quiz;
import com.bluecode.chatbot.domain.QuizType;
import com.bluecode.chatbot.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    // 특정 챕터, 특정 유형의 n개의 문제를 뽑는 메서드
    public List<Quiz> getRandomQuizzesByType(int chapterNum, QuizType type, int count) {
        logger.info("getRandomQuizzesByType chapterNum: {}, type: {}, count: {}", chapterNum, type, count);

        // 주어진 챕터와 유형에 맞는 모든 퀴즈를 담은 리스트
        List<Quiz> quizzes = quizRepository.findAllByChapNum(chapterNum)
                .stream()
                .filter(quiz -> quiz.getQuizType() == type)
                .collect(Collectors.toList());

        // 퀴즈가 존재하지 않을 경우
        if (quizzes.isEmpty()) {
            logger.warn("No quizzes found for chapterNum: {} and type: {}", chapterNum, type);
            return Collections.emptyList();
        }

        // 퀴즈 리스트를 랜덤하게 섞음
        Collections.shuffle(quizzes, new Random());

        // count로 정한 수만큼 문제를 선택하여 담은 리스트
        List<Quiz> selectedQuizzes = quizzes.stream()
                .limit(count)
                .collect(Collectors.toList());

        logger.info("Selected quizzes: {}", selectedQuizzes);
        return selectedQuizzes;
    }
}