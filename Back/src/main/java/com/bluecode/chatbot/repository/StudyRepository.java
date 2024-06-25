package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Studies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Studies, Long> {
    List<Studies> findAllByUserIdAndCurriculumId(Long userId, Long curriculumId);
    Studies findByUserIdAndCurriculumId(Long userId, Long curriculumId);
}