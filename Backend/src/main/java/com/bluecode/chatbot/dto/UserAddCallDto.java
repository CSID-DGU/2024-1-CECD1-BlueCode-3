package com.bluecode.chatbot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddCallDto {
    private String username;
    private String email;
    private String id;
    private String password;
    private String birth;
}
