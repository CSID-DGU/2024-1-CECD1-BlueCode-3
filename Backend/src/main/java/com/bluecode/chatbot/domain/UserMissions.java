package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UserMissions {

    // table id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_mission_id")
    private Long userMissionId;

    // 대상 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // 시작 날짜
    private LocalDate startDate;

    // 완료해야 할 날짜
    private LocalDate endDate;

    // 클리어한 날짜
    private LocalDateTime clearDateTime;

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
            clearDateTime = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public static UserMissions createUserMission(
            Users user,
            LocalDate startDate,
            LocalDate endDate,
            Missions mission,
            int currentCount,
            MissionStatus missionStatus
    ) {
        UserMissions userMission = new UserMissions();
        userMission.setUser(user);
        userMission.setStartDate(startDate);
        userMission.setEndDate(endDate);
        userMission.setMission(mission);
        userMission.setCurrentCount(currentCount);
        userMission.setMissionStatus(missionStatus);

        return userMission;
    }
}
