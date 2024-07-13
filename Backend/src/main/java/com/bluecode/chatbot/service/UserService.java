package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.dto.UserAddCallDto;
import com.bluecode.chatbot.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    // 유저 테이블 id 기반 user 검색
    public Users findById(Long userId){
        return userRepository.findByUserId(userId)
                .orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }

    // 유저 추가 (회원 가입)
    @Transactional
    public void addUser(UserAddCallDto userAddCallDto){
        // 가입 DTO 넘어올때 중복 체크
        if(userRepository.existsByUsername(userAddCallDto.getUsername())){
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(userAddCallDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepository.existsById(userAddCallDto.getId())) {
            throw new IllegalArgumentException("Id already exists");
        }

        // password는 bcrypt로 암호화하여 DB에 저장
        String encodedPassword=bCryptPasswordEncoder.encode(userAddCallDto.getPassword());
        Users user=Users.createUser(userAddCallDto.getUsername(),
                userAddCallDto.getEmail(),
                userAddCallDto.getId(),
                encodedPassword,
                userAddCallDto.getBirth(),
                false);
        userRepository.save(user);
    }

    public boolean checkUserNameDuplicate(String name){
        return userRepository.existsByUsername(name);
    }
    public boolean checkEmailDuplicate(String email){
        return userRepository.existsByEmail(email);
    }
    public boolean checkIdDuplicate(String id){
        return userRepository.existsById(id);
    }
}
