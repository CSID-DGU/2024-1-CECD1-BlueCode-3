package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Missions {

    @Id @GeneratedValue
    @Column(name = "mission_id")
    private Long missionId;

    // 클리어시 제공하는 경험치
    private int exp;

    @Enumerated(EnumType.STRING)
    private MissionType mType;

    // 미션 내용
    private String text;

    // 미션 달성 목표 횟수
    private int missionCount;
}
