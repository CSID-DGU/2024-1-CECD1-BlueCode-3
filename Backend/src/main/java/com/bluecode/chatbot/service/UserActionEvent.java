package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.ServiceType;
import com.bluecode.chatbot.domain.Users;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 특정 Service 활동 미션 진행을 위한 event 클래스
 */

@Getter
public class UserActionEvent extends ApplicationEvent {

    private Users user;
    private ServiceType serviceType;


    public UserActionEvent(Object source, Users user, ServiceType serviceType) {
        super(source);
        this.user = user;
        this.serviceType = serviceType;
    }
}
