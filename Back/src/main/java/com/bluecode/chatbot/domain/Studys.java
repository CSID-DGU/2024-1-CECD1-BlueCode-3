package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Studys {

    @Id @GeneratedValue
    @Column(name = "study_id")
    private Long studyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id")
    private Curriculums curriculum;

    @Column(name = "total_time")
    private Long totalTime;

    @Column(name = "start_day")
    private LocalDate startDay;

    @Column(name = "text")
    private String text;

    @Column(name = "passed")
    private boolean passed;
}
