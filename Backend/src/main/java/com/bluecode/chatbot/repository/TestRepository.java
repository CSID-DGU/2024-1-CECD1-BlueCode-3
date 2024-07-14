package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Tests, Long> {

    // 유저 테이블 id, 퀴즈 테이블 id 기반 test 단일 검색
    @Query("select t from Tests t " +
            "join fetch t.user " +
            "join fetch t.quiz " +
            "where t.user.userId = :userId " +
            "and t.quiz.quizId = :quizId")
    Optional<Tests> findByUserIdAndQuizId(@Param("userId") Long userId,
                                          @Param("quizId") Long quizId);

    // 유저 테이블 id 기반 커리큘럼 챕터 번호 정렬 test 리스트 검색
    @Query("select t from Tests t " +
            "join fetch t.user " +
            "join fetch t.quiz " +
            "where t.user.userId = :userId " +
            "order by t.quiz.curriculum.chapterNum")
    List<Tests> findAllByUserId(@Param("userId") Long userId);

    // 유저 테이블 id와 테스트 타입 기반 test 리스트 검색
    @Query("select t from Tests t " +
            "join fetch t.user " +
            "join fetch t.quiz " +
            "where t.user.userId = :userId " +
            "and t.testType = :testType")
    List<Tests> findAllByUserIdAndTestType(@Param("userId") Long userId,
                                           @Param("testType") TestType testType);

    // 유저 테이블 id와 커리큘럼 id 기반 tests 리스트 검색
    @Query("select t from Tests t " +
            "join fetch t.user " +
            "join fetch t.quiz " +
            "join fetch t.quiz.curriculum " +
            "where t.user.userId = :userId " +
            "and t.quiz.curriculum.curriculumId = :curriculumId ")
    List<Tests> findAllByUserIdAndCurriculumId(@Param("userId") Long userId,
                                               @Param("curriculumId") Long curriculumId);

    // 유저 테이블 id와 커리큘럼 id 퀴즈 타입, 퀴즈 레벨 기반 tests 리스트 검색
    @Query("select t from Tests t " +
            "join fetch t.user " +
            "join fetch t.quiz " +
            "join fetch t.quiz.curriculum " +
            "where t.user.userId = :userId " +
            "and t.quiz.curriculum.curriculumId = :curriculumId " +
            "and t.quiz.quizType = :quizType " +
            "and t.quiz.level = :quizLevel")
    List<Tests> findByUserIdAndCurriculumIdAndQuizTypeAndLevel(@Param("userId") Long userId,
                                                               @Param("curriculumId") Long curriculumId,
                                                               @Param("quizType") QuizType quizType,
                                                               @Param("quizLevel")QuizLevel quizLevel);
}
