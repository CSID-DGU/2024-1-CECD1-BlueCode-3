package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
