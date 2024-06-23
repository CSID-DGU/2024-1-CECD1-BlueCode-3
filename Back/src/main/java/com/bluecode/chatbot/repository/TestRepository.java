package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Tests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Tests, Long> {
}
