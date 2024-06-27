package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    // 유저 테이블 id 기반 user 검색
    Optional<Users> findByUserId(Long userId);

    // 유저 로그인 id 기반 user 검색
    Optional<Users> findByLoginId(String loginId);
}
