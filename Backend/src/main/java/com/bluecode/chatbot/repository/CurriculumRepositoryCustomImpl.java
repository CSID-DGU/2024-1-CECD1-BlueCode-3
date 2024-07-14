package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Curriculums;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CurriculumRepositoryCustomImpl implements CurriculumRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Curriculums> findAllRootCurriculumList() {

        return em.createQuery("select c from Curriculums c where c.chapterNum = 0", Curriculums.class).getResultList();
    }

    @Override
    public Optional<Curriculums> findByRootIdAndChapterNum(Long rootCurriculumId, int chapterNum) {
        return Optional.ofNullable(em.createQuery("select c from Curriculums c " +
                        "join fetch c.parent " +
                        "where c.parent.curriculumId = :rootId and c.chapterNum = :chapNum", Curriculums.class)
                .setParameter("rootId", rootCurriculumId)
                .setParameter("chapNum", chapterNum)
                .getSingleResult());
    }
}
