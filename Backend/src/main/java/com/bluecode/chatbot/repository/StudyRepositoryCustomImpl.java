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
                                 "join fetch s.curriculum " +
                                 "join fetch s.user " +
                                 "where s.curriculum.curriculumId = :curriculumId " +
                                 "and s.user.userId = :userId", Studies.class)
                .setParameter("curriculumId", curriculumId)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Studies> findAllByUserId(Long userId){

        return em.createQuery("select s from Studies s " +
                                 "where s.user.userId = :userId ", Studies.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
