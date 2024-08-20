package com.bluecode.chatbot.dto;

import com.bluecode.chatbot.domain.LevelType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class initChapterPassRequestDto {
    private Long userId;
    private Long curriculumId;
    private LevelType level;
}
