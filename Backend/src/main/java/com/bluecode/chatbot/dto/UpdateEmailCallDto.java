package com.bluecode.chatbot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmailCallDto {
    private Long userId;
    private String email;
}
