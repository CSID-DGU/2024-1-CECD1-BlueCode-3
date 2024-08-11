package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Studies {

    // table id
    @Id @GeneratedValue
    @Column(name = "study_id")
    private Long studyId;

    // 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // 커리큘럼
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id")
    private Curriculums curriculum;

    // 총 학습 시간
    @Column(name = "total_time")
    private Long totalTime;

    // 시작 날짜
    @Column(name = "start_day")
    @CreationTimestamp
    private LocalDate startDay;

    // 학습 내용 전문 - 이론
    @Column(name = "text_def", columnDefinition = "MEDIUMTEXT")
    private String textDef;

    // 학습 내용 전문 - 실습
    @Column(name = "text_code", columnDefinition = "MEDIUMTEXT")
    private String textCode;

    // 학습 내용 전문 - 연습문제
    @Column(name = "text_quiz", columnDefinition = "MEDIUMTEXT")
    private String textQuiz;

    // 해당 학습 통과 여부
    @Column(name = "passed")
    private boolean passed;

    // 난이도
    @Enumerated(EnumType.STRING)
    private LevelType level;

    public static Studies createStudy(
            Users user,
            Curriculums curriculum,
            boolean passed,
            String textDef,
            String textCode,
            String textQuiz,
            LevelType level
    ) {
        Studies studies = new Studies();
        studies.setUser(user);
        studies.setCurriculum(curriculum);
        studies.setPassed(passed);
        studies.setTextDef(textDef);
        studies.setTextCode(textCode);
        studies.setTextQuiz(textQuiz);
        studies.setLevel(level);

        return studies;
    }
}
