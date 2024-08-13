package com.bluecode.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class QuizCase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizCaseId;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private String input;

    private String output;

    public static QuizCase createQuizCase(Quiz quiz, String input, String output) {
        QuizCase quizCase = new QuizCase();
        quizCase.setQuiz(quiz);
        quizCase.setInput(input);
        quizCase.setOutput(output);

        return quizCase;
    }
}
