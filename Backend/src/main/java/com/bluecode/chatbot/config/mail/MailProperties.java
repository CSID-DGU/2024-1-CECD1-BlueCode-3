package com.bluecode.chatbot.config.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "mail")
public class MailProperties {
    private String hostname;
    private int port;
    private String username;
    private String password;
}
