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
            Users user1 = createUser("testName", "testEmail", "testId", "1111", "11110033", false); // 초기 테스트 미진행 유저
            em.persist(user1);
        }

        private Users createUser(
                String username,
                String email,
                String id,
                String password,
                String birth,
                boolean initTest) {

            Users user = new Users();
            user.setUsername(username);
            user.setId(id);
            user.setEmail(email);
            user.setPassword(password);
            user.setBirth(birth);
            user.setInitTest(initTest);

            return user;
        }
    }
}
