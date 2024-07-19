package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.config.jwt.CookieUtil;
import com.bluecode.chatbot.config.jwt.TokenProvider;
import com.bluecode.chatbot.domain.RefreshToken;
import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.dto.LoginCallDto;
import com.bluecode.chatbot.dto.LoginResponseDto;
import com.bluecode.chatbot.repository.RefreshTokenRepository;
import com.bluecode.chatbot.repository.UserRepository;
import com.bluecode.chatbot.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(2);
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(7);
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginCallDto loginCallDto, HttpServletResponse response){

        // login call dto 의 id,pw 확인해서 auth 체킹
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginCallDto.getId(),loginCallDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // user 정보 찾아서 액세스 발행 , 리프레시 토큰 기존있으면 불러오고 없으면 새로 발행
        Users user=userRepository.findByLoginId(loginCallDto.getId()).orElseThrow(()->new RuntimeException("user not found user id :" + loginCallDto.getId()));

        String accessToken=tokenProvider.generateToken(user,ACCESS_TOKEN_DURATION);
        Optional<RefreshToken> refreshToken=refreshTokenService.createOrGetRefreshToken(user);
        String refreshTokenString=refreshToken.get().getRefreshToken();

        LoginResponseDto loginResponseDto=new LoginResponseDto();
        loginResponseDto.setAccessToken(accessToken);
        loginResponseDto.setRefreshToken(refreshTokenString);

        // 두 토큰을 dto로도 반환하고, 리프레시토큰을 쿠키로도 반환
        CookieUtil.addCookie(response,REFRESH_TOKEN_COOKIE_NAME,refreshTokenString,(int) REFRESH_TOKEN_DURATION.toSeconds());
        return ResponseEntity.ok().body(loginResponseDto);
    }
}
