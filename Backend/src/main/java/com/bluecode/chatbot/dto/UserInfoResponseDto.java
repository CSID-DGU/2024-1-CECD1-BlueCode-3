package com.bluecode.chatbot.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserInfoResponseDto {
    int exp;
    int tier;
    boolean initTest;
    String username;
    String birth;
    String email;
    Long userId;
    String id;
    private LocalDateTime registerDateTime;
}
