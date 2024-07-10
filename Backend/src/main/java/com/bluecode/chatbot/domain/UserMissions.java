package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UserMissions {

    // table id
    @Id @GeneratedValue
    @Column(name = "user_mission_id")
    private Long userMissionId;

    // 대상 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // 생성 날짜
    private LocalDateTime startDate;

    // 클리어 날짜
    private LocalDateTime endDate;

    // 할당된 미션
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Missions mission;

    // 현재 달성 현황
    @Column(name = "current_count")
    private int currentCount;

    // 미션 수행 현황(진행중, 클리어, 실패)
    @Enumerated(EnumType.STRING)
    private MissionStatus missionStatus;

    // 미션 진행도 처리 method
    public boolean incrementProgress() {
        // 미션을 완료 또는 실패한 상태이면
        if (missionStatus == MissionStatus.COMPLETED || missionStatus == MissionStatus.FAILED) {
            return false;
        }

        currentCount++;

        if (currentCount >= mission.getMissionCount()) {
            missionStatus = MissionStatus.COMPLETED;
            return true;
        }
        return false;
    }
}
