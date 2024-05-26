package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserMissions {

    @Id @GeneratedValue
    @Column(name = "usermission_id")
    private Long userMissionId;


    // 대상 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // 할당된 미션
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Missions mission;

    // 현재 달성 현황
    @Column(name = "current_count", columnDefinition = "0")
    private int currentCount;
}
