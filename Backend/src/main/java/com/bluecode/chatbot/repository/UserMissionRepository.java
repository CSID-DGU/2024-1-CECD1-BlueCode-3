package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.UserMissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMissionRepository extends JpaRepository<UserMissions, Long> {
}
