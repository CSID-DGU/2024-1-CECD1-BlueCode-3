package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Quiz;
import com.bluecode.chatbot.domain.QuizLevel;
import com.bluecode.chatbot.domain.QuizType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizRepositoryCustom {

    // 커리큘럼 id 기반 quiz 리스트 검색
    @Query("select q from Quiz q " +
            "join fetch q.curriculum " +
            "where q.curriculum.curriculumId = :curriculumId")
    List<Quiz> findAllByCurriculumId(@Param("curriculumId") Long curriculumId);

    // 커리큘럼 id, level 기반 quiz 리스트 검색
    @Query("select q from Quiz q " +
            "join fetch q.curriculum " +
            "where q.curriculum.curriculumId = :curriculumId " +
            "and q.level = :level")
    List<Quiz> findByCurriculumIdAndLevel(@Param("curriculumId") Long curriculumId, @Param("level") QuizLevel levelType);

    // 커리큘럼 id, 퀴즈 타입 기반 quiz 리스트 검색
    @Query("select q from Quiz q " +
            "join fetch q.curriculum " +
            "where q.curriculum.curriculumId = :curriculumId " +
            "and q.quizType = :quizType")
    List<Quiz> findAllByCurriculumIdAndQuizType(@Param("curriculumId") Long curriculumId, @Param("quizType") QuizType quizType);

    // 커리큘럼 id, 퀴즈 타입, level 기반 quiz 리스트 검색
    @Query("select q from Quiz q " +
            "join fetch q.curriculum " +
            "where q.curriculum.curriculumId = :curriculumId " +
            "and q.quizType = :quizType " +
            "and q.level = :level")
    List<Quiz> findAllByCurriculumIdAndQuizTypeAndLevel(@Param("curriculumId") Long curriculumId,
                                                                     @Param("quizType") QuizType quizType,
                                                                     @Param("level") QuizLevel quizLevel);
}
