package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Missions {

    // table id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long missionId;

    // 클리어시 제공하는 경험치
    private int exp;

    // 미션 타입
    @Enumerated(EnumType.STRING)
    private MissionType missionType;

    // 대상 Service 타입
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    // 대상 Action 타입
    private String actionType;

    // 미션 내용
    private String text;

    // 미션 달성 목표 횟수
    private int missionCount;

    public static Missions createMission(
            int exp,
            MissionType missionType,
            ServiceType serviceType,
            String actionType,
            String text,
            int missionCount
    ) {
        Missions mission = new Missions();
        mission.setExp(exp);
        mission.setMissionType(missionType);
        mission.setServiceType(serviceType);
        mission.setActionType(actionType);
        mission.setText(text);
        mission.setMissionCount(missionCount);

        return mission;
    }
}
