package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.dto.EmailVerifyDto;
import com.bluecode.chatbot.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/user/email/send/{email}")
    public ResponseEntity<String> getCode(@PathVariable String email){
        emailService.sendCodeMail(email);
        return ResponseEntity.ok().body("이메일에 코드 전송 완료");
    }

    @PostMapping("/user/email/verify")
    public ResponseEntity verifyCode(@RequestBody EmailVerifyDto emailVerifyDto){
        try {
            Boolean verify_result=emailService.verifyCode(emailVerifyDto);
            return ResponseEntity.ok().body(verify_result);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
