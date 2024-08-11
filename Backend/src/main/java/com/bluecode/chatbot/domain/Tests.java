package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Tests {

    // table id
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
    @CreationTimestamp
    private LocalDateTime testDate;

    // 완료 시간
    @UpdateTimestamp
    private LocalDateTime solvedDate;

    // 점수
    @Column(name = "wrong_count")
    private int wrongCount;

    // 통과 여부
    private boolean passed;

    // 테스트 타입(초기, 이해도)
    @Enumerated(EnumType.STRING)
    private TestType testType;

    public static Tests createTest(
            Users user,
            Quiz quiz,
            int wrongCount,
            boolean passed,
            TestType testType
    ) {
        Tests test = new Tests();
        test.setUser(user);
        test.setQuiz(quiz);
        test.setWrongCount(wrongCount);
        test.setPassed(passed);
        test.setTestType(testType);

        return test;
    }
}
