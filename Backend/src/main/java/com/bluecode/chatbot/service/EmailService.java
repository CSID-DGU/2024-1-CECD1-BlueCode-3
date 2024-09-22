package com.bluecode.chatbot.service;

import com.bluecode.chatbot.config.redis.RedisUtil;
import com.bluecode.chatbot.dto.EmailVerifyDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    // 이메일 보내는 메서드
    public void sendCodeMail(String email){
        MimeMessage message=javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper helper=new MimeMessageHelper(message,true,"UTF-8");
            String code=generateSecureRandomCode();

            helper.setTo(email);
            helper.setSubject("코드 인증 번호 입니다.");
            helper.setText(htmlContent(code), true);

            javaMailSender.send(message);

            // redis에 해당 이메일-인증코드 데이터가 존재하면 삭제 (중복 문제)
            if(redisUtil.existData(email)){
                redisUtil.deleteData(email);
            }
            redisUtil.setDataExpire(email,code,60*3L);

        } catch (MessagingException e){
            log.error("error in mail sending : "+ e.getMessage());
        }
    }


    public Boolean  verifyCode(EmailVerifyDto emailVerifyDto){
        String codeFromRedis=redisUtil.getData(emailVerifyDto.getEmail());
        log.info("redis 에 "+emailVerifyDto.getEmail()+"와 매핑되는 코드 : "+codeFromRedis);

        if(codeFromRedis == null){
            return false;
        }
        return codeFromRedis.equals(emailVerifyDto.getCode());
    }


    // html + 인증 코드 폼
    public String htmlContent(String code){
        return "<html>" +
                "<body>" +
                "<h3>안녕하세요 !</h3><br>" +
                "<p>인증 번호는 <b>"+code+"</b>입니다.</p><br>" +
                "<p>인증 번호의 유효시간은 3분입니다</p>" +
                "</body>" +
                "</html>";
    }

    // 길이가 6인 숫자 난수 생성
    public String generateSecureRandomCode() {

        int CODE_LENGTH = 6;
        SecureRandom RANDOM = new SecureRandom();

        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int digit = RANDOM.nextInt(10); // Generate a number between 0 and 9
            code.append(digit);
        }
            return code.toString();
    }


}



