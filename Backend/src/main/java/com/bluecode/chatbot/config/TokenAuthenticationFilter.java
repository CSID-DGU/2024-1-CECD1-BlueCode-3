package com.bluecode.chatbot.config;

import com.bluecode.chatbot.config.jwt.TokenProvider;
import com.bluecode.chatbot.config.jwt.TokenValidationResult;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION="Authorization";
    private final static String TOKEN_PREFIX="Bearer ";

    // request 헤더에서 토큰 추출, 유효성 검사 후 인증 정보 설정
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{

        String requestURI = request.getRequestURI();

        // 특정 경로에 대해서는 필터를 적용하지 않음
        if (requestURI.startsWith("/api/") || requestURI.startsWith("/user/") || requestURI.startsWith("/mission/init" )) {
            filterChain.doFilter(request, response);
            return;
        }

        //  request 에서 토큰 분리
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        String token = getAccessToken(authorizationHeader);

        TokenValidationResult tokenValidationResult=tokenProvider.validateToken(token);
        switch (tokenValidationResult) {
            case VALID:
                // 토큰이 유효할 경우 인증 객체를 설정하고 필터 체인을 계속 진행
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
                break;
            case EXPIRED:
                // 토큰이 만료된 경우 401 Unauthorized 응답
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token is expired");
                break;
            case INVALID:
                // 그 외의 모든 유효하지 않은 경우 401 Unauthorized 응답
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token validation error");
                break;
            default:
                // 예상치 못한 결과에 대한 기본 응답
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Unexpected error occurred during token validation");
                break;
        }
    }

    // 접두사 제거 메서드
    private String getAccessToken(String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)){
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

}
