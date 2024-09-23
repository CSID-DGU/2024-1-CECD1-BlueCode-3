package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.MissionConst;
import com.bluecode.chatbot.domain.ServiceType;
import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.dto.UpdateEmailCallDto;
import com.bluecode.chatbot.dto.UpdatePasswordCallDto;
import com.bluecode.chatbot.dto.UserAddCallDto;
import com.bluecode.chatbot.dto.UserInfoResponseDto;
import com.bluecode.chatbot.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 미션 처리를 위한 클래스
    private final ApplicationEventPublisher eventPublisher;

    // 유저 테이블 id 기반 user 검색
    public Users findById(Long userId){
        return userRepository.findByUserId(userId)
                .orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }

    // 유저 정보 반환
    public UserInfoResponseDto getUserInfo(Long userId){
        Optional<Users> user=userRepository.findByUserId(userId);
        if(user.isEmpty()){
            throw new IllegalArgumentException("유저 ID 존재하지 않음");
        }

        UserInfoResponseDto responseDto=new UserInfoResponseDto();

        responseDto.setUserId(user.get().getUserId());
        responseDto.setEmail(user.get().getEmail());
        responseDto.setTier(user.get().getTier());
        responseDto.setExp(user.get().getExp());
        responseDto.setBirth(user.get().getBirth());
        responseDto.setUsername(user.get().getUsername());
        responseDto.setInitTest(user.get().isInitTest());
        responseDto.setId(user.get().getId());
        responseDto.setRegisterDateTime(user.get().getRegisterDateTime());
        return responseDto;
    }


    // 유저 추가 (회원 가입)
    @Transactional
    public Long addUser(UserAddCallDto userAddCallDto){
        // 가입 DTO 넘어올때 중복 체크
        //if(userRepository.existsByUsername(userAddCallDto.getUsername())){
        //    throw new IllegalArgumentException("Username already exists");
        //}
        if (userRepository.existsByEmail(userAddCallDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepository.existsById(userAddCallDto.getId())) {
            throw new IllegalArgumentException("Id already exists");
        }

        // password는 bcrypt로 암호화하여 DB에 저장
        String encodedPassword=bCryptPasswordEncoder.encode(userAddCallDto.getPassword());
        Users user=Users.createUser(userAddCallDto.getUsername(),
                userAddCallDto.getEmail(),
                userAddCallDto.getId(),
                encodedPassword,
                userAddCallDto.getBirth(),
                false);
        userRepository.save(user);
        return user.getUserId();
    }

    // 유저 이름 , 이메일로 저장되어있는 로그인 id 반환
    public String findLoginId(UserAddCallDto userAddCallDto) throws Exception{
        Optional<Users> user=userRepository.findByUserNameAndEmail(userAddCallDto.getUsername(),userAddCallDto.getEmail());
        if(user.isEmpty()){
            throw new IllegalArgumentException("유저 ID 존재하지 않음");
        }
        log.info("해당되는 로그인 id " + user.get().getId());
        return user.get().getId();
    }

    // 비밀번호 수정
    @Transactional
    public void updatePassword(UpdatePasswordCallDto updatePasswordCallDto) throws Exception{
        Optional<Users> user=userRepository.findByUserId(updatePasswordCallDto.getUserId());
        if(user.isEmpty()){
            throw new IllegalArgumentException("유저 ID 존재하지 않음");
        }
        log.info("해당되는 로그인 id " + user.get().getId());
        String encodedPassword=bCryptPasswordEncoder.encode(updatePasswordCallDto.getPassword());
        user.get().setPassword(encodedPassword);
    }

    // 이메일 수정
    @Transactional
    public void updateEmail(UpdateEmailCallDto updateEmailCallDto) throws Exception{
        Optional<Users> user=userRepository.findByUserId(updateEmailCallDto.getUserId());
        if(user.isEmpty()){
            throw new IllegalArgumentException("유저 ID 존재하지 않음");
        }
        log.info("해당되는 로그인 id " + user.get().getId());
        user.get().setEmail(updateEmailCallDto.getEmail());
    }

    @Transactional
    public void updateLoginStreak(Users user){

        //초기 회원가입 유저는 null 로 되어있기에 업데이트용
        if(user.getRecentAccess() == null){
            user.setRecentAccess(LocalDateTime.now());
        }
        LocalDate lastLoginDate=user.getRecentAccess().toLocalDate();
        LocalDate today=LocalDate.now();
        log.info("가장 최근 로그인 " +lastLoginDate);

        if(lastLoginDate.isEqual(today.minusDays(1))){
            //하루 단위로 차이가 나면 = 하루마다 접속 시
            user.setStreakCount(user.getStreakCount()+1);

            // 연속 로그인 미션 처리
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.USER, MissionConst.createConstByUserStreakDay(user.getStreakCount())));

        } else if(!lastLoginDate.isEqual(today)){
            // 하루 이상 차이나면 1로 초기화
            user.setStreakCount(1);
        }
        user.setRecentAccess(LocalDateTime.now());
    }

    public boolean checkUserNameDuplicate(String name){
        return userRepository.existsByUsername(name);
    }
    public boolean checkEmailDuplicate(String email){
        return userRepository.existsByEmail(email);
    }
    public boolean checkIdDuplicate(String id){
        return userRepository.existsById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findByLoginId(id).orElseThrow(()-> new UsernameNotFoundException("cannot find id --> "+ id));
    }
}
