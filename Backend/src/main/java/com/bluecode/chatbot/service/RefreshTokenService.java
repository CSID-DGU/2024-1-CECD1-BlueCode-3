package com.bluecode.chatbot.service;

import com.bluecode.chatbot.config.jwt.TokenProvider;
import com.bluecode.chatbot.config.jwt.TokenValidationResult;
import com.bluecode.chatbot.domain.RefreshToken;
import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    // refresh 토큰 검색 메서드
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected refresh token"));
    }

    // user id로 refreshtoken 검색
    public Optional<RefreshToken> getRefreshTokenByUserId(String userId) {
        return refreshTokenRepository.findByUser_Id(userId);
    }

    // 기존 리프레시 토큰 있으면 반환하고 없으면 새롭게 만들고 리프레시 토큰 레포에 저장 후 반환
    // + 기존 리프레시 토큰의 유효기간이 지나있으면 재발급하고 반환
    @Transactional
    public Optional<RefreshToken> createOrGetRefreshToken(Users users) {
        Optional<RefreshToken> existingToken = getRefreshTokenByUserId(users.getId());
        if (existingToken.isEmpty()) {
            String newRefreshToken = tokenProvider.generateToken(users, Duration.ofDays(7));
            RefreshToken refreshTokenObject = new RefreshToken(users, newRefreshToken);
            return Optional.of(refreshTokenRepository.save(refreshTokenObject));
        } else {
            TokenValidationResult tokenValidationResult = tokenProvider.validateToken(existingToken.get().getRefreshToken());
            switch (tokenValidationResult) {
                case EXPIRED:   //기존 리프레시토큰이 만료되어있으면 재발급후 반환
                    String newRefreshToken = tokenProvider.generateToken(users, Duration.ofDays(7));
                    existingToken.get().update(newRefreshToken);
                    return existingToken;
                default:
                    return existingToken;
            }
        }
    }
}
