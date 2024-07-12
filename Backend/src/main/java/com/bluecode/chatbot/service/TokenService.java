package com.bluecode.chatbot.service;

import com.bluecode.chatbot.config.jwt.TokenProvider;
import com.bluecode.chatbot.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    // access token 생성 서비스 코드
    public String createNewAccessToken(String refreshToken){
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected refresh token(not valid)");
        }
        //Long userId=refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        Long userId=refreshTokenService.findByRefreshToken(refreshToken).getUser().getUserId();
        Users user=userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
