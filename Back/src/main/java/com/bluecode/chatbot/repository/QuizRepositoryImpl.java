package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Quiz;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<Quiz> findAllByChapNum(int chapterNum) {

        return em.createQuery("select distinct q from Quiz q join fetch q.curriculum c where c.chapterNum = :chapNum", Quiz.class)
                .setParameter("chapNum", chapterNum)
                .getResultList();
    }
}
