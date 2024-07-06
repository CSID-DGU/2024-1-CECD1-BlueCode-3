package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Curriculums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculums, Long>, CurriculumRepositoryCustom {

    // 부모 커리큘럼 class 기반 커리큘럼 chapterNum 순서로 전체 챕터 커리큘럼 검색
    List<Curriculums> findAllByParentOrderByChapterNum(Curriculums parent);
}
