package com.bluecode.chatbot.config.jwt;

import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Slf4j
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken 테스트")
    @Test
    void generateToken(){

        // 토큰 테스트팅용 유저 생성
        Users testUser = Users.createUser("token_user", "token_testEmail", "token_testId", "4444", "22223344", true);
        userRepository.save(testUser);

        // 유저 데이터로 토큰 생성
        String token=tokenProvider.generateToken(testUser, Duration.ofDays(14));


        //jjwt 라이브러리르 통해 토큰 복호화, 토큰을 만들때 클레임으로 넣어둔 id값이 일치하는지 확인
        Long userId= Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);


        assertThat(userId).isEqualTo(testUser.getUserId());
        log.info(String.valueOf(Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()));
    }

    @DisplayName("validToken 테스트 ")
    @Test
    void validToken_invalidToken() {

        // 팩토리 패턴으로 expired 된 토큰 생성, 나머지는 디폴트
        String token_fail = JwtFactory.builder()
                .expire(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        boolean result_fail= tokenProvider.validToken(token_fail);
        assertThat(result_fail).isFalse();

        // 팩토리 패턴으로 디폴트 토큰(expired 되지 않은 ) 생성
        String token_success = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);

        boolean result_success= tokenProvider.validToken(token_success);

        assertThat(result_success).isTrue();

    }

    @DisplayName("getAuthentication 테스트")
    @Test
    void getAuthentication() {

        String userName = "user_test";
        String token = JwtFactory.builder()
                .subject(userName)
                .build()
                .createToken(jwtProperties);

        Authentication authentication= tokenProvider.getAuthentication(token);

        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userName);
        log.info(String.valueOf(authentication));

    }

    @DisplayName("getUserId 테스트")
    @Test
    void getUserId() {

        Long userId = 3L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        Long userIdByToken = tokenProvider.getUserId(token);

        assertThat(userIdByToken).isEqualTo(userId);
        log.info(String.valueOf(userIdByToken));
    }

}
