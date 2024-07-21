package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Quiz {

    // table id
    @Id @GeneratedValue
    @Column(name = "quiz_id")
    private Long quizId;

    // 대상 커리큘럼
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id")
    private Curriculums curriculum;

    // 문제 타입(객관식, 코드작성식, 단답식)
    @Enumerated(EnumType.STRING)
    private QuizType quizType;

    // 문제 내용
    private String text;

    // 문제 정답
    private String answer;

    // 문제 난이도
    @Enumerated(EnumType.STRING)
    private QuizLevel level;

    // 객관식 전용 선지(1 - 4)
    private String q1;

    private String q2;

    private String q3;

    private String q4;

    // 코드작성식 전용
    private String inputs;

    private String outputs;

    // 단답식 전용(단어 글자 수 표시 용)
    private int wordCount;
}
