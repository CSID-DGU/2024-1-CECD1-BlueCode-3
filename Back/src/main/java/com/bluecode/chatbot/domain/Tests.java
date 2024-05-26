package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
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
    @Column(name = "wrong_count", columnDefinition = "0")
    private int wrongCount;

    // 통과 여부
    private boolean passed;

    @Enumerated(EnumType.STRING)
    private TestType tType;
}
