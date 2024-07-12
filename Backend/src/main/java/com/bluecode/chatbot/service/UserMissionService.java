package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.repository.MissionRepository;
import com.bluecode.chatbot.repository.UserMissionRepository;
import com.bluecode.chatbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserMissionService {

    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;

    // 미션 진행 현황 체크 및 보상 제공 method
    public void checkAndCompleteMission(Users user, ServiceType serviceType, Long userMissionId) throws Exception {

        Optional<UserMissions> userMission = userMissionRepository.findById(userMissionId);

        if (userMission.isEmpty()) {
            log.error("userMission이 존재하지 않습니다. userId: {}, userMissionId: {}", user.getUserId(), userMissionId);
            throw new IllegalArgumentException("userMission이 존재하지 않습니다.");
        }

        if (!userMission.get().getMission().getServiceType().equals(serviceType)) {
            log.error("mission 내의 serviceType과 제공된 serviceType이 일치하지 않습니다. userMission 내 ServiceType: {}, 주어진 ServiceType: {}", userMission.get().getMission().getServiceType(), serviceType);
            throw new IllegalArgumentException("mission 내의 serviceType과 제공된 serviceType이 일치하지 않습니다.");
        }

        if (userMission.get().incrementProgress()) {
            user.setExp(user.getExp() + userMission.get().getMission().getExp());

        }

        userMissionRepository.save(userMission.get());
    }

    // 미션 할당 method
    public void assignMission(Users user, MissionType missionType, int missionCount) {

        List<Missions> missions = missionRepository.findAllByMissionType(missionType);

        if (missions.isEmpty()) {
            throw new IllegalArgumentException(missionType + " 인 미션이 존재하지 않습니다.");
        }

        Collections.shuffle(missions);

        int count = 0;
        for (Missions mission : missions) {
            if (count >= missionCount) break;

            UserMissions userMission = new UserMissions();
            userMission.setUser(user);
            userMission.setMission(mission);
            userMission.setCurrentCount(0);
            userMission.setStartDate(LocalDate.now());
            userMission.setEndDate(calculateEndTime(missionType));
            userMission.setMissionStatus(MissionStatus.PROGRESS);
            userMissionRepository.save(userMission);
            count++;
        }
    }

    // 미션 제한 시간 계산
    private LocalDate calculateEndTime(MissionType missionType) {
        if (missionType == MissionType.DAILY) {
            return LocalDate.now().plusDays(1);
        } else if (missionType == MissionType.WEEKLY) {
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        } else {
            return null; // challenge 미션은 종료 시간 없음
        }
    }

    // 특정 미션 초기화(일일, 주간 미션용)
    public void resetMissions(MissionType missionType, int missionCount) {
        // 진행중인 (일일 / 주간)미션 검색
        List<UserMissions> userMissions = userMissionRepository.findByMissionTypeAndMissionStatus(missionType, MissionStatus.PROGRESS);
        for (UserMissions userMission : userMissions) {
            if (LocalDate.now().isAfter(userMission.getEndDate())) {
                userMission.setMissionStatus(MissionStatus.FAILED); // 완료하지 못한 미션 실패 처리 진행
                userMission.setClearDateTime(null);
                userMissionRepository.save(userMission);
            }
        }

        // 모든 유저 대상 미션 재할당 진행
        List<Users> users = userRepository.findAll();
        for (Users user : users) {
            assignMission(user, missionType, missionCount); // 미션 missionCount 개 할당
        }
    }

}
