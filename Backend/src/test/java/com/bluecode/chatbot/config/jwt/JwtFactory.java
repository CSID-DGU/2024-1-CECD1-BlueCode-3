package com.bluecode.chatbot.config.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

// 테스팅용 토큰 생성 팩토리 패턴
@Getter
public class JwtFactory {
    private String subject = "default_user";
    private Date issuedAt=new Date();
    private Date expire = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());
    private Map<String,Object> claims= Collections.emptyMap();

    @Builder
    public JwtFactory(String subject,Date issuedAt, Date expire,Map<String,Object> claims){
        this.subject = subject != null ? subject : this.subject;
        this.issuedAt = issuedAt != null ? issuedAt : this.issuedAt;
        this.expire = expire != null ? expire : this.expire;
        this.claims = claims != null ? claims : this.claims;
    }

    public static JwtFactory withDefaultValues(){
        return JwtFactory.builder().build();
    }

    public String createToken(JwtProperties jwtProperties){
        return Jwts.builder()
                .setSubject(subject)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setExpiration(expire)
                .setIssuedAt(issuedAt)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256,jwtProperties.getSecretKey())
                .compact();
    }
}
