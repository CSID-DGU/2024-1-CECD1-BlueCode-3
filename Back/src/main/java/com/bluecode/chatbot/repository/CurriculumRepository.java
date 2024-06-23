package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Curriculums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculums, Long> {

    List<Curriculums> findAllByParentOrderByCurriculumId(Curriculums parent);

    Curriculums findByChapterNum(int chapterNum);
}
