package com.bluecode.chatbot.domain;

import jakarta.persistence.*;

@Entity
public class Quiz {

    @Id @GeneratedValue
    @Column(name = "quiz_id")
    private Long quizId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id")
    private Curriculums curriculum;

    @Enumerated(EnumType.STRING)
    private QuizType qType;

    // 문제 내용
    private String text;

    // 문제 정답
    private String answer;

    // 문제 수준
    @Enumerated(EnumType.STRING)
    private QuizLevel level;

    private int q1;

    private int q2;

    private int q3;

    private int q4;
}
