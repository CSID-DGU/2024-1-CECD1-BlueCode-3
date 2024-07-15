package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.dto.UserMissionDataCallDto;
import com.bluecode.chatbot.dto.UserMissionDataResponseDto;
import com.bluecode.chatbot.service.UserMissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserMissionController {

    private final UserMissionService userMissionService;

    @PostMapping("/mission/find")
    public ResponseEntity findMissions(@RequestBody UserMissionDataCallDto dto) {

        try {
            UserMissionDataResponseDto result = userMissionService.findMissions(dto);
            return ResponseEntity.ok(result);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
