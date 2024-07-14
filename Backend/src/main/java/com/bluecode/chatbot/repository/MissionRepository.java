package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.MissionType;
import com.bluecode.chatbot.domain.Missions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Missions, Long> {

    // 미션 타입 기반 리스트 검색
    @Query("select m from Missions m where m.missionType = :missionType")
    List<Missions> findAllByMissionType(@Param("missionType")MissionType missionType);
}
