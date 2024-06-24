package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Curriculums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculums, Long> {

    // 부모 커리큘럼으로 커리큘럼 검색
    List<Curriculums> findAllByParentOrderByChapterNum(Curriculums parent);

    // 챕터 순번(chapterNum)으로 커리큘럼 검색
    Curriculums findByChapterNum(int chapterNum);
}
