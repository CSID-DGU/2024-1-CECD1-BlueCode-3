package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Studies;

import java.util.List;

public interface StudyRepositoryCustom {

    // 커리큘럼 id 와 유저 id 기반 Studies 리스트 검색
    List<Studies> findAllByCurriculumIdAndUserId(Long curriculumId, Long userId);
}
