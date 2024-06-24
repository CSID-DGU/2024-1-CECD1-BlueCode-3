package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Studies;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StudyRepositoryCustomImpl implements StudyRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Studies> findAllByCurriculumIdAndUserId(Long curriculumId, Long userId) {

        return em.createQuery("select s from Studies s " +
                                 "join fetch Curriculums c " +
                                 "join fetch Users u " +
                                 "where c.curriculumId = :curriculumId " +
                                 "and u.userId = :userId", Studies.class)
                .setParameter("curriculumId", curriculumId)
                .setParameter("userId", userId)
                .getResultList();
    }
}
