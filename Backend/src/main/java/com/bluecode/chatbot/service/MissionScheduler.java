package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.MissionType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 일간, 주간 미션 주기적 초기화 진행 클래스
 */

@Service
@RequiredArgsConstructor
public class MissionScheduler {

    private final UserMissionService userMissionService;

    // 주기적으로 생성될 일일 미션 개수
    private final int dailyMissionCount = 3;

    // 주기적으로 생성될 주간 미션 개수
    private final int weeklyMissionCount = 3;


    @Scheduled(cron = "0 0 0 * * ?")
    public void resetDailyMissions() {
        userMissionService.resetMissions(MissionType.DAILY, dailyMissionCount);
    }

    @Scheduled(cron = "0 0 0  * MON")
    public void resetWeeklyMissions() {
        userMissionService.resetMissions(MissionType.WEEKLY, weeklyMissionCount);
    }
}
