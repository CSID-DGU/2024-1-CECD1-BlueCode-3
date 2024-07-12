package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    // 유저 테이블 id 기반 user 검색
    public Users findById(Long userId){
        return userRepository.findByUserId(userId)
                .orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }
}
