package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Curriculums {

    @Id @GeneratedValue
    @Column(name = "curriculum_id")
    private Long curriculumId;

    // 부모 정의
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Curriculums parent;

    // 자식 정의
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Curriculums> children = new ArrayList<>();

    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();

    // 커리큘럼 이름
    private String curriculumName;

    // 내용
    private String text;
}
