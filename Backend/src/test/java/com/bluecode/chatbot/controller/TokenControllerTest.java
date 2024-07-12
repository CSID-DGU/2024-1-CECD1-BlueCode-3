package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.config.jwt.JwtFactory;
import com.bluecode.chatbot.config.jwt.JwtProperties;
import com.bluecode.chatbot.domain.RefreshToken;
import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.dto.AccessTokenCallDto;
import com.bluecode.chatbot.repository.RefreshTokenRepository;
import com.bluecode.chatbot.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @DisplayName("createNewAccessToken api 테스트")
    @Test
    public void createNewAccesToken() throws Exception{
        final String url="/api/token";

        //토큰 발급 테스팅용 유저 생성
        Users testUser = Users.createUser("token_user", "token_testEmail", "token_testId", "4422", "22223344", true);
        userRepository.save(testUser);

        // db에 기존 refreshToken이 저장되어있다고 가정하기 위해 refreshToken 생성
        String refreshToken= JwtFactory.builder()
                .claims(Map.of("id", testUser.getUserId()))
                .build()
                .createToken(jwtProperties);
        //refreshTokenRepository.save(new RefreshToken(testUser.getUserId(), refreshToken));
        refreshTokenRepository.save(new RefreshToken(testUser, refreshToken));

        AccessTokenCallDto request=new AccessTokenCallDto();
        request.setRefreshToken(refreshToken);
        final String requestBody=objectMapper.writeValueAsString(request);

        ResultActions resultActions=mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andDo(print());
    }

}
