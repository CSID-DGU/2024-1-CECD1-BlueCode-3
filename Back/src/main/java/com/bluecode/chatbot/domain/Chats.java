package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Chats {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long chatId;

    // 사용 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // 사용 커리큘럼
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id")
    private Curriculums curriculum;

    // 채팅한 시간
    private LocalDateTime chatDate;

    // 질문
    private String question;

    // 답변
    private String answer;

    // 질문 유형
    @Column(name = "q_type")
    @Enumerated(EnumType.STRING)
    private QuestionType qType;
}
