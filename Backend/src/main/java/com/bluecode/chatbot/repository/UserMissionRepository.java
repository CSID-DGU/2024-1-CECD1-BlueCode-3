package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.MissionType;
import com.bluecode.chatbot.domain.UserMissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMissionRepository extends JpaRepository<UserMissions, Long> {


    // 유저 테이블 id와 미션 테이블 id 기반 단일 검색
    @Query("select um from UserMissions um " +
            "join fetch um.user " +
            "join fetch um.mission " +
            "where um.user.userId = :userId " +
            "and um.mission.missionId = :missionId")
    Optional<UserMissions> findByUserIdAndMissionId(@Param("userId") Long userId,
                                                    @Param("missionId") Long missionId);


    // 유저 테이블 id 와 미션 타입 기반 미완료 미션 리스트 검색
    @Query("select um from UserMissions um " +
            "join fetch um.user " +
            "join fetch um.mission " +
            "where um.user.userId = :userId " +
            "and um.mission.missionType = :missionType " +
            "and um.currentCount < um.mission.missionCount")
    List<UserMissions> findUnsolvedMissionByUserIdAndMissionType(@Param("userId") Long userId,
                                                                   @Param("missionType") MissionType missionType);

    // 유저 테이블 id 와 미션 타입 기반 완료 미션 리스트 검색
    @Query("select um from UserMissions um " +
            "join fetch um.user " +
            "join fetch um.mission " +
            "where um.user.userId = :userId " +
            "and um.mission.missionType = :missionType " +
            "and um.currentCount = um.mission.missionCount")
    List<UserMissions> findSolvedMissionByUserIdAndMissionType(@Param("userId") Long userId,
                                                                 @Param("missionType") MissionType missionType);
}
