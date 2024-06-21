package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Users {

    // table id
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    // 유저 이름
    private String username;

    // 이메일
    private String email;

    // 유저 아이디
    private String id;

    // 유저 비밀번호
    private String password;

    // 유저 전화번호
    private String phoneNumber;

    // 최근 접속일
    private LocalDateTime recent_access;

    // 연속 접속일수
    private int streakCount;

    // 생년월일
    private String birth;

    // 티어
    private int tier;

    // 현재 경험치
    private int exp;

    // 최초 시험 수행 여부
    private boolean initTest;

    // 진행중인 커리큘럼
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Studys> studys = new ArrayList<>();

    // 진행한 테스트
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Tests> tests = new ArrayList<>();

    // 진행중인 미션
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserMissions> userMissions = new ArrayList<>();
}
