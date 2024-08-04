package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.LevelType;
import com.bluecode.chatbot.domain.Studies;
import com.bluecode.chatbot.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyRepository extends JpaRepository<Studies, Long>, StudyRepositoryCustom {

    // 유저 테이블 id, 커리큘럼 id, level 기반 Studies 단일 검색
    @Query("select s from Studies s " +
            "join fetch s.user " +
            "join fetch s.curriculum " +
            "where s.user.userId = :userId " +
            "and s.curriculum.curriculumId = :curriculumId" +
            " and s.level = :levelType")
    Optional<Studies> findByUserIdAndCurriculumIdAndLevel(@Param("userId") Long userId, @Param("curriculumId") Long curriculumId, @Param("levelType") LevelType levelType);

    // 유저, 커리큘럼(챕터), level 기반 Studies 단일 검색
    Optional<Studies> findByCurriculumAndUserAndLevel(Curriculums chapter, Users user, LevelType levelType);

    // 유저, 루크 커리큘럼 기반 Studies 리스트 검색
    @Query("select s from Studies s " +
            "join fetch s.user " +
            "join fetch s.curriculum " +
            "where s.user = :user " +
            "and s.curriculum.parent = :root")
    List<Studies> findAllByUserAndRoot(@Param("user") Users user, @Param("root") Curriculums root);

    // 유저, 루트 커리큘럼 기반 난이도가 EASY 인 Studies 리스트 검색
    @Query("select s from Studies s " +
            "join fetch s.user " +
            "join fetch s.curriculum " +
            "where s.user = :user " +
            "and s.curriculum.parent = :root " +
            "and s.level = com.bluecode.chatbot.domain.LevelType.EASY")
    List<Studies> findAllEasyStudiesByUserAndRoot(@Param("user") Users user, @Param("root") Curriculums root);

    // 유저, 챕터 기반 Studies 리스트 검색
    List<Studies> findAllByUserAndCurriculum(Users user, Curriculums chapter);

    // 유저 기반 Studies 리스트 검색
    List<Studies> findAllByUser(Users user);
}
