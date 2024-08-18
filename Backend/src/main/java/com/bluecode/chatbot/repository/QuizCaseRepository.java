package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.QuizCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCaseRepository extends JpaRepository<QuizCase, Long> {
}
