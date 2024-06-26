package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    // 유저 테이블 id 기반 user 검색
    Users findByUserId(Long userId);
}
