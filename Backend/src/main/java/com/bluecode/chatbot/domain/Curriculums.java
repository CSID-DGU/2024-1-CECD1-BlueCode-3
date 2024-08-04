package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Curriculums {

    // table id
    @Id @GeneratedValue
    @Column(name = "curriculum_id")
    private Long curriculumId;

    // 부모 커리큘럼 정의
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Curriculums parent;

    // 루트 커리큘럼 정의
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_id")
    private Curriculums root;

    // 커리큘럼 이름
    private String curriculumName;

    // 테스트 진행 커리큘럼 여부
    private boolean testable;

    // 챕터 순서
    private int chapterNum;

    // 서브 챕터 순서
    private int subChapterNum;

    // 루트/챕터 커리큘럼 내 챕터/서브챕터 총 개수(root, chapter 전용)
    private int totalChapterCount;

    // 루트 커리큘럼 여부
    private boolean rootNode;

    // 서브챕터 커리큘럼 여부
    private boolean leafNode;

    public static Curriculums createCurriculum(
            Curriculums parent,
            Curriculums root,
            String curriculumName,
            boolean testable,
            int chapterNum,
            int subChapterNum,
            int totalChapterCount,
            boolean isLeaf,
            boolean isRoot
    ) {
        Curriculums curriculums = new Curriculums();
        curriculums.setCurriculumName(curriculumName);
        curriculums.setParent(parent);
        curriculums.setRoot(root);
        curriculums.setTestable(testable);
        curriculums.setSubChapterNum(subChapterNum);
        curriculums.setChapterNum(chapterNum);
        curriculums.setTotalChapterCount(totalChapterCount);
        curriculums.setRootNode(isRoot);
        curriculums.setLeafNode(isLeaf);

        return curriculums;
    }
}
