package com.bluecode.chatbot.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Tests {

    @Id @GeneratedValue
    @Column(name = "test_id")
    private Long testId;

    // 대상 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // 사용 문제
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    // 시험 시작 시간
    private LocalDateTime testDate;


    // 완료 시간
    private LocalDateTime solvedDate;

    // 점수
    private int score;

    // 통과 여부
    private int passed;
}
