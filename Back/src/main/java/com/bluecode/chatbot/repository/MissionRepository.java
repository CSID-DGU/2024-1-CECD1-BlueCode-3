package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Missions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Missions, Long> {
}
