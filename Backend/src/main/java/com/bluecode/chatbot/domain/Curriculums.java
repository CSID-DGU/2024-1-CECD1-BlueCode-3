package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Curriculums {

    // table id
    @Id @GeneratedValue
    @Column(name = "curriculum_id")
    private Long curriculumId;

    // 부모 커리큘럼 정의
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

    // 입문자 키워드
    private String keywordEasy;

    // 초급자 키워드
    private String keywordNormal;

    // 중급자 키워드
    private String keywordHard;

    // 테스트 진행 커리큘럼 여부
    private boolean testable;

    // 챕터 순서
    private int chapterNum;

    // 루크 커리큘럼 내 챕터 총 개수(root 전용)
    private int totalChapterCount;
}
