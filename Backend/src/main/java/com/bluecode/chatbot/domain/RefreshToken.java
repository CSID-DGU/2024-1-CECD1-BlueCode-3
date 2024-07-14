package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id",updatable = false)
    private Long tokenId;

    //유저와 일대일 매핑
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Users user;

    @Column(name = "refresh_token",nullable = false)
    private String refreshToken;

    public RefreshToken(Users user, String refreshToken) {
        this.user = user;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken){
        this.refreshToken=newRefreshToken;
        return this;
    }

}
