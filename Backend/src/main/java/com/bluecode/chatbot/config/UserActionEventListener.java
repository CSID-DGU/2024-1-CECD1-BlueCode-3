package com.bluecode.chatbot.config;

import com.bluecode.chatbot.domain.MissionStatus;
import com.bluecode.chatbot.domain.UserMissions;
import com.bluecode.chatbot.repository.UserMissionRepository;
import com.bluecode.chatbot.service.UserMissionService;
import com.bluecode.chatbot.service.UserActionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserActionEventListener {

    private final UserMissionService userMissionService;
    private final UserMissionRepository userMissionRepository;

    // 미션 처리를 위한 event 처리 method
    @EventListener
    public void handleUserActionEvent(UserActionEvent event) throws Exception {

        List<UserMissions> userMissions = userMissionRepository.findAllByUserAndServiceTypeAndMissionStatus(event.getUser(), event.getServiceType(), MissionStatus.PROGRESS);
        log.info("미션 조회: userId: {}, userMissions.size(): {}", event.getUser().getUserId(), userMissions.size());

        for (UserMissions userMission : userMissions) {
            if (userMission.getMission().getServiceType().equals(event.getServiceType())) {
                try {
                    log.info("미션 수행 여부 체크: userId: {}, Mission 내 ServiceType: {}, event 내 ServiceType: {}, userMissionId: {}", event.getUser().getUserId(), userMission.getMission().getServiceType(), event.getServiceType(), userMission.getUserMissionId());
                    userMissionService.checkAndCompleteMission(event.getUser(), userMission.getMission().getServiceType(), userMission.getUserMissionId());
                } catch (Exception e) {
                    log.error("handleUserActionEvent 에러 발생: " + e.getMessage());
                }

            }
        }
    }
}
