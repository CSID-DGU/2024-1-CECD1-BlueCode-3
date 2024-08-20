package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Curriculums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculums, Long> {

    // 루트 커리큘럼 기반 챕터 리스트 검색 + 챕터 커리큘럼 기반 서브 챕터 리스트 검색(chapterNum & subChapterNum 정렬)
    @Query("select c from Curriculums c " +
            "join fetch c.root " +
            "join fetch c.parent " +
            "where c.parent = :parent " +
            "order by c.chapterNum, c.subChapterNum")
    List<Curriculums> findAllChildByParentOrderByChapterNumAndSubChapterNum(@Param("parent") Curriculums parent);


    // root 커리큘럼 기반 루트 파생 커리큘럼 리스트 검색
    List<Curriculums> findAllByRoot(Curriculums root);

    // root 커리큘럼, 리프 노드 여부 기반 커리큘럼 리스트 검색
    @Query("select c from Curriculums c " +
            "join fetch c.root " +
            "join fetch c.parent " +
            "left join fetch c.before " +
            "left join fetch c.next " +
            "where c.root = :root " +
            "and c.leafNode = :leafNode " +
            "order by c.chapterNum asc, c.subChapterNum asc")
    List<Curriculums> findAllByRootAndLeafNodeOrderByChapterNumAndSubChapterNum(Curriculums root, boolean leafNode);

    // 루트 커리큘럼 리스트 검색
    @Query("select c from Curriculums c " +
            "where c.rootNode = true")
    List<Curriculums> findAllRootCurriculumList();
}
