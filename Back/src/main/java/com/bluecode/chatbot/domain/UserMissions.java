package com.bluecode.chatbot.domain;

import jakarta.persistence.*;

@Entity
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
}
