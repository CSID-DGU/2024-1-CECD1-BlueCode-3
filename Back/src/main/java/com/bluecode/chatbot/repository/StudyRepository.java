package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.LevelType;
import com.bluecode.chatbot.domain.Studies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
