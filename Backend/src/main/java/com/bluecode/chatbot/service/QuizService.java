package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.Quiz;
import com.bluecode.chatbot.domain.QuizLevel;
import com.bluecode.chatbot.domain.QuizType;
import com.bluecode.chatbot.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;

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
                .collect(Collectors.toList());

        log.info("Selected quizzes: {}", selectedQuizzes);
        return selectedQuizzes;
    }

    // 특정 커리큘럼, 특정 퀴즈 레벨의 n개의 문제를 뽑는 메서드
    public List<Quiz> getRandomQuizzesByLevel(Curriculums curriculums, QuizLevel quizLevel, int count) {

        // 루트 커리큘럼을 대상으로 호출 시 Exception 발생
        if (curriculums.getChapterNum() == 0) {
            throw new IllegalArgumentException("루트 커리큘럼은 유효하지 않습니다.");
        }

        List<Quiz> quizzes = quizRepository.findByCurriculumIdAndLevel(curriculums.getCurriculumId(), quizLevel);
        log.info("getRandomQuizzesByType curriculum: {}, level: {}, count: {}", curriculums.getCurriculumName(), quizLevel, count);

        // 퀴즈가 존재하지 않을 경우 Exception 발생
        if (quizzes.isEmpty()) {
            throw new IllegalArgumentException("대상 커리큘럼에 해당하는 문제가 존재하지 않습니다.");
        }

        // 퀴즈 리스트를 랜덤하게 섞음
        Collections.shuffle(quizzes, new Random());

        // count로 정한 수만큼 문제를 선택하여 담은 리스트
        List<Quiz> selectedQuizzes = quizzes.stream()
                .limit(count)
                .collect(Collectors.toList());

        return selectedQuizzes;
    }
}