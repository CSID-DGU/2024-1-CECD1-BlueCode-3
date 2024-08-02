package com.bluecode.chatbot.dto;

import com.bluecode.chatbot.domain.MissionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMissionDataElementDto {

    private String text;
    private Integer currentCount;
    private Integer missionCount;
    private MissionStatus missionStatus;

}
