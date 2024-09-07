package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Chats {

    // table id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    // 대상 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // 대상 커리큘럼
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id")
    private Curriculums curriculum;

    // 채팅한 시간
    @CreationTimestamp
    private LocalDateTime chatDate;

    // 질문
    private String question;

    // 답변
    @Column(name = "answer", columnDefinition = "MEDIUMTEXT")
    private String answer;

    // 질문 유형
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    // 단계적 질문 진행 현황
    private int level;

    public static Chats createChat(Users user,
                             Curriculums curriculum,
                             String question,
                             String answer,
                             QuestionType questionType,
                             int level) {
        Chats chat = new Chats();
        chat.setUser(user);
        chat.setCurriculum(curriculum);
        chat.setQuestion(question);
        chat.setAnswer(answer);
        chat.setQuestionType(questionType);
        chat.setLevel(level);

        return chat;
    }
}
