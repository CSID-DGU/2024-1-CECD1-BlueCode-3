package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.*;
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

    // 유저 테이블 id, 미션 테이블 id, 미션 진행 현황, 미션 타입 기반 리스트 검색
    @Query("select um from UserMissions um " +
            "join fetch um.user " +
            "join fetch um.mission " +
            "where um.user.userId = :userId " +
            "and um.mission.missionId = :missionId " +
            "and um.missionStatus = :missionStatus " +
            "and um.mission.missionType = :missionType")
    List<UserMissions> findByUserIdAndMissionIdAndMissionStatusAndMissionType(@Param("userId") Long userId,
                                                                    @Param("missionId") Long missionId,
                                                                    @Param("missionStatus") MissionStatus missionStatus,
                                                                    @Param("missionType") MissionType missionType);

    // 미션 타입, 미션 상태 기반 리스트 검색 (주기적 미션 초기화 용)
    @Query("select um from UserMissions um " +
            "join fetch um.user " +
            "join fetch um.mission " +
            "where um.missionStatus = :missionStatus " +
            "and um.mission.missionType = :missionType")
    List<UserMissions> findByMissionTypeAndMissionStatus(@Param("missionType") MissionType missionType,
                                                         @Param("missionStatus") MissionStatus missionStatus);

    // 유저, 대상 service, 미션 진행 현황 기반 리스트 검색
    List<UserMissions> findAllByUserAndMissionStatus(Users user, MissionStatus missionStatus);

    @Query("select um from UserMissions um " +
            "join fetch um.user " +
            "join fetch um.mission " +
            "where um.missionStatus = :missionStatus " +
            "and um.mission.serviceType = :serviceType " +
            "and um.user = :user")
    List<UserMissions> findAllByUserAndServiceTypeAndMissionStatus(@Param("user") Users user,
                                                                   @Param("serviceType") ServiceType serviceType,
                                                                   @Param("missionStatus") MissionStatus missionStatus);
}
