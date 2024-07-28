package com.bluecode.chatbot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVerifyDto {
    String email;
    String code;
}
