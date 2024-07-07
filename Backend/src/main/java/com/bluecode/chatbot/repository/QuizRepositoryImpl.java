package com.bluecode.chatbot.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizRepositoryCustom{

    private final EntityManager em;

}
