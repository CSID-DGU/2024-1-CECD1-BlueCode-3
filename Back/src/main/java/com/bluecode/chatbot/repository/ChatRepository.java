package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Chats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chats, Long> {
}
