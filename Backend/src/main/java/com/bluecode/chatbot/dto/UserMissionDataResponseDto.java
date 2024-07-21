package com.bluecode.chatbot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserMissionDataResponseDto {

    List<UserMissionDataElementDto> listDaily;
    List<UserMissionDataElementDto> listWeekly;
    List<UserMissionDataElementDto> listChallenge;
}
