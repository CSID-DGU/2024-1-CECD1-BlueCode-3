package com.bluecode.chatbot.dto;

import com.bluecode.chatbot.domain.StudyStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurriculumRootElementDto {

    private Long rootId;
    private String title;
    private StudyStatus status;
}
