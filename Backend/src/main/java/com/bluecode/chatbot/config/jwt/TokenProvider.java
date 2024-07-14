package com.bluecode.chatbot.config.jwt;

import com.bluecode.chatbot.domain.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;

    public String generateToken(Users user, Duration expire){
        Date now=new Date();
        return makeToken(new Date(now.getTime()+expire.toMillis()),user);
    }

    // jwt 토큰 생성
    private String makeToken(Date expiry, Users user){
        Date now=new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(user.getUsername()) //토큰 페이로드 설정 (set)
                .claim("id",user.getUserId())   // 토큰 클레임 설정
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // jwt 토큰 인증 체킹
    public boolean validToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;  //복호화 에러시 false
        }
    }

    // jwt 토큰으로 인증 정보 가져옴
    public Authentication getAuthentication(String token){
        Claims claims=getClaims(token);

        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(claims.getSubject(),"",authorities),token,authorities
        );
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserId(String token){
        Claims claims=getClaims(token);
        return claims.get("id", Long.class);
    }

}
