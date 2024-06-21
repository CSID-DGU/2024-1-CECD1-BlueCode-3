package com.bluecode.chatbot.config;

import com.bluecode.chatbot.domain.Users;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 테스트용 유저 데이터를 DB에 저장하는 class 입니다.
 */

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.userInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void userInit() {
            Users user1 = createUser("testName", "testEmail", "testId", "000-0000-0000", "11110033");
            em.persist(user1);
        }

        private Users createUser(
                String username,
                String email,
                String id,
                String phoneNumber,
                String birth) {

            Users user = new Users();
            user.setUsername(username);
            user.setId(id);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setBirth(birth);

            return user;
        }
    }
}
