package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
public class Users implements UserDetails {

    // table id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    // 유저 이름
    private String username;

    // 이메일
    private String email;

    // 유저 아이디
    private String id;

    // 유저 비밀번호
    private String password;

    // 최근 접속일
    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime recentAccess;

    // 가입 일자
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registerDateTime;

    // 연속 접속일수
    private int streakCount;

    // 생년월일
    private String birth;

    // 티어
    private int tier;

    // 현재 경험치
    private int exp;

    // 최초 시험 수행 여부
    private boolean initTest;

    // Users 생성 로직
    public static Users createUser(
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
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
