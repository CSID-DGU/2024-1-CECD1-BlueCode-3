package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.config.jwt.CookieUtil;
import com.bluecode.chatbot.config.jwt.TokenProvider;
import com.bluecode.chatbot.domain.MissionConst;
import com.bluecode.chatbot.domain.RefreshToken;
import com.bluecode.chatbot.domain.ServiceType;
import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.dto.LoginCallDto;
import com.bluecode.chatbot.dto.LoginResponseDto;
import com.bluecode.chatbot.repository.UserRepository;
import com.bluecode.chatbot.service.RefreshTokenService;
import com.bluecode.chatbot.service.UserActionEvent;
import com.bluecode.chatbot.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    private final UserService userService;

    // 미션 처리를 위한 클래스
    private final ApplicationEventPublisher eventPublisher;

    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(2);
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(7);
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    @PostMapping("/login")
    public ResponseEntity authenticate(@RequestBody LoginCallDto loginCallDto, HttpServletResponse response){

        try {
            // login call dto 의 id,pw 확인해서 auth 체킹
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginCallDto.getId(), loginCallDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // user 정보 찾아서 액세스 발행 , 리프레시 토큰 기존있으면 불러오고 없으면 새로 발행
            Users user = userRepository.findByLoginId(loginCallDto.getId()).orElseThrow(() -> new RuntimeException("user not found user id :" + loginCallDto.getId()));

            //연속 로그인 업데이트
            userService.updateLoginStreak(user);

            // 미션 처리 로직
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.USER, MissionConst.USER_LOGIN));

            String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
            Optional<RefreshToken> refreshToken = refreshTokenService.createOrGetRefreshToken(user);
            String refreshTokenString = refreshToken.get().getRefreshToken();

            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setAccessToken(accessToken);
            loginResponseDto.setUserid(user.getUserId());
            // 두 토큰을 dto로도 반환하고, 리프레시토큰을 쿠키로도 반환
            CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshTokenString, (int) REFRESH_TOKEN_DURATION.toSeconds());
            return ResponseEntity.ok().body(loginResponseDto);

        } catch (BadCredentialsException e){
            //사용자 이름 또는 비밀번호가 잘못되었을 때, 아이디,비밀번호 재입력 요청으로 연결
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Id or password");
        } catch (AuthenticationException e){
            //인증 과정 로직에서 오류
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

    }
}
