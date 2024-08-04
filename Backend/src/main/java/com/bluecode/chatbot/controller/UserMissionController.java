package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.domain.MissionType;
import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.dto.UserMissionDataCallDto;
import com.bluecode.chatbot.dto.UserMissionDataResponseDto;
import com.bluecode.chatbot.service.MissionScheduler;
import com.bluecode.chatbot.service.UserMissionService;
import com.bluecode.chatbot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserMissionController {

    private final UserMissionService userMissionService;
    private final MissionScheduler missionScheduler;
    private final UserService userService;

    // 미션 현황 조회
    @PostMapping("/mission/find")
    public ResponseEntity<UserMissionDataResponseDto> findMissions(@RequestBody UserMissionDataCallDto dto) {


        UserMissionDataResponseDto result = userMissionService.findMissions(dto);
        return ResponseEntity.ok(result);
    }

    // 최초 접근자용 미션 할당
    @PostMapping("/mission/init")
    public ResponseEntity<String> initMissions(@RequestBody UserMissionDataCallDto dto) {

        Users user = userService.findById(dto.getUserId());

        userMissionService.assignMission(user, MissionType.DAILY, 3);
        userMissionService.assignMission(user, MissionType.WEEKLY, 3);
        userMissionService.assignChallengeMission(user);

        return ResponseEntity.ok("ok");
    }

    // 주기적 초기화 테스트용 강제 호출 method
    @GetMapping("/admin/mission/reset/daily")
    public ResponseEntity<String> resetMission() {
        missionScheduler.resetDailyMissions();
        return ResponseEntity.ok("ok");

    }

    // 주기적 초기화 테스트용 강제 호출 method
    @GetMapping("/admin/mission/reset/weekly")
    public ResponseEntity<String> resetWeeklyMission() {
        missionScheduler.resetWeeklyMissions();
        return ResponseEntity.ok("ok");

    }
}
