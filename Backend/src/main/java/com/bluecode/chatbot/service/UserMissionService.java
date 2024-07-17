package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.dto.UserMissionDataCallDto;
import com.bluecode.chatbot.dto.UserMissionDataElementDto;
import com.bluecode.chatbot.dto.UserMissionDataResponseDto;
import com.bluecode.chatbot.repository.MissionRepository;
import com.bluecode.chatbot.repository.UserMissionRepository;
import com.bluecode.chatbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
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

    // 미션 현황 조회 method
    public UserMissionDataResponseDto findMissions(@RequestBody UserMissionDataCallDto dto) {

        log.info("UserMissionController findMissions 시작");
        Optional<Users> user = userRepository.findByUserId(dto.getUserId());

        if (user.isEmpty()) {
            log.error("유효하지 않은 유저 id: {}", dto.getUserId());
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        // 조회 결과를 담을 리스트
        List<UserMissions> daily = new ArrayList<>();
        List<UserMissions> weekly = new ArrayList<>();
        List<UserMissions> challenge = new ArrayList<>();

        // 주기 내 진행중인 일일 미션
        daily.addAll(userMissionRepository.findAllProgressMissionByUserAndMissionType(user.get(), MissionType.DAILY));
        // 주기 내 완료 일일 미션
        daily.addAll(userMissionRepository.findAllCompleteMissionByUserAndStartDateAndMissionType(user.get(), LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)), MissionType.DAILY));

        // 주기 내 진행중인 주간 미션
        weekly.addAll(userMissionRepository.findAllProgressMissionByUserAndMissionType(user.get(), MissionType.WEEKLY));
        // 주기 내 완료 주간 미션
        weekly.addAll(userMissionRepository.findAllCompleteMissionByUserAndStartDateAndMissionType(user.get(), LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)), MissionType.WEEKLY));

        // 진행중인 도전 과제
        challenge.addAll(userMissionRepository.findAllByUserAndMissionStatusAndType(user.get(), MissionStatus.PROGRESS, MissionType.CHALLENGE));
        // 완료한 도전 과제
        challenge.addAll(userMissionRepository.findAllByUserAndMissionStatusAndType(user.get(), MissionStatus.COMPLETED, MissionType.CHALLENGE));

        List<UserMissionDataElementDto> dailyDto = new ArrayList<>();
        List<UserMissionDataElementDto> weeklyDto = new ArrayList<>();
        List<UserMissionDataElementDto> challengeDto = new ArrayList<>();

        // return 리스트
        UserMissionDataResponseDto result = new UserMissionDataResponseDto();

        // dto mapping
        for (UserMissions um : daily) {
            UserMissionDataElementDto element = new UserMissionDataElementDto();
            element.setText(um.getMission().getText());
            element.setMissionCount(um.getMission().getMissionCount());
            element.setCurrentCount(um.getCurrentCount());
            element.setMissionStatus(um.getMissionStatus());

            dailyDto.add(element);
        }

        // dto mapping
        for (UserMissions um : weekly) {
            UserMissionDataElementDto element = new UserMissionDataElementDto();
            element.setText(um.getMission().getText());
            element.setMissionCount(um.getMission().getMissionCount());
            element.setCurrentCount(um.getCurrentCount());
            element.setMissionStatus(um.getMissionStatus());

            weeklyDto.add(element);
        }

        // dto mapping
        for (UserMissions um : challenge) {
            UserMissionDataElementDto element = new UserMissionDataElementDto();
            element.setText(um.getMission().getText());
            element.setMissionCount(um.getMission().getMissionCount());
            element.setCurrentCount(um.getCurrentCount());
            element.setMissionStatus(um.getMissionStatus());

            challengeDto.add(element);
        }

        result.setListDaily(dailyDto);
        result.setListWeekly(weeklyDto);
        result.setListChallenge(challengeDto);

        return result;
    }


    // 미션 진행 현황 체크 및 보상 제공 method
    public void checkAndCompleteMission(Users user, String actionType, Long userMissionId) throws Exception {

        Optional<UserMissions> userMission = userMissionRepository.findById(userMissionId);

        if (userMission.isEmpty()) {
            log.error("userMission이 존재하지 않습니다. userId: {}, userMissionId: {}", user.getUserId(), userMissionId);
            throw new IllegalArgumentException("userMission이 존재하지 않습니다.");
        }

        if (!userMission.get().getMission().getActionType().equals(actionType)) {
            log.error("mission 내의 actionType과 제공된 actionType이 일치하지 않습니다. userMission 내 actionType: {}, 주어진 actionType: {}", userMission.get().getMission().getActionType(), actionType);
            throw new IllegalArgumentException("mission 내의 actionType과 제공된 actionType이 일치하지 않습니다.");
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

    // 도전 과제 미션 할당 method
    public void assignChallengeMission(Users user) {

        List<Missions> missions = missionRepository.findAllByMissionType(MissionType.CHALLENGE);

        Optional<UserMissions> valid = userMissionRepository.findByUserIdAndMissionId(user.getUserId(), missions.get(0).getMissionId());

        if (valid.isPresent()) {
            throw new IllegalArgumentException("이미 해당 유저는 도전과제를 생성하였습니다.");
        }

        for (Missions mission : missions) {
            UserMissions userMission = new UserMissions();
            userMission.setUser(user);
            userMission.setMission(mission);
            userMission.setCurrentCount(0);
            userMission.setStartDate(LocalDate.now());
            userMission.setEndDate(calculateEndTime(MissionType.CHALLENGE));
            userMission.setMissionStatus(MissionStatus.PROGRESS);
            userMissionRepository.save(userMission);
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
