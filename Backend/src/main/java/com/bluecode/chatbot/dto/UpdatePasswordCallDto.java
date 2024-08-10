package com.bluecode.chatbot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordCallDto {
    private Long userId;
    private String password;
}
