package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.dto.AccessTokenCallDto;
import com.bluecode.chatbot.dto.AccessTokenResponseDto;
import com.bluecode.chatbot.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<AccessTokenResponseDto> createNewAccessToken(@RequestBody AccessTokenCallDto request){
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AccessTokenResponseDto(newAccessToken));
    }
}
