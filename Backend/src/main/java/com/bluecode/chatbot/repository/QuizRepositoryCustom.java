package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Quiz;

import java.util.List;

public interface QuizRepositoryCustom {

    List<Quiz> findAllByChapNum(int chapterNum);
}
