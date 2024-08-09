package com.bluecode.chatbot.controller;


import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.dto.UserAddCallDto;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
    }

    @DisplayName("username,email,id duplicate api 테스트")
    @Test
    public void duplicateTest() throws Exception{
        final String url_username="/user/exists/username/{username}";
        final String url_email="/user/exists/email/{email}";
        final String url_id="/user/exists/id/{id}";

        // init db에 이미 있는 유저 네임 ""testName2" 중복 체크
        final ResultActions resultActions_username=mockMvc.perform(get(url_username,"testName2"));
        resultActions_username.andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print());

        // init db에 없는 이메일 "not_duplicated" 중복 체크
        final ResultActions resultActions_email=mockMvc.perform(get(url_email,"not_duplicated"));
        resultActions_email.andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andDo(print());

        // init db에 있는 id "testId2" 중복 체크
        final ResultActions resultActions_id=mockMvc.perform(get(url_id,"testId2"));
        resultActions_id.andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print());
    }


    @DisplayName("adduser api 테스트")
    @Test
    public void addUser() throws Exception{
        final String url="/user/create";

        final String username="adduserTestName";
        final String email="adduserTestEmail";
        final String id="adduserTestId";
        final String password="5123";
        final String birth="111111";
        final UserAddCallDto userAddCallDto= new UserAddCallDto();
        userAddCallDto.setUsername(username);
        userAddCallDto.setEmail(email);
        userAddCallDto.setId(id);
        userAddCallDto.setPassword(password);
        userAddCallDto.setBirth(birth);
        final String requestBody= objectMapper.writeValueAsString(userAddCallDto);

        ResultActions resultActions=mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions.andExpect(status().isOk()).andDo(print());

    }

    @DisplayName("find user api 테스트")
    @Test
    public void findUser() throws Exception{
        final String url="/user/findId";

        final String username="testName2";
        final String email="testEmail2";
        final String id="";
        final String password="";
        final String birth="";
        final UserAddCallDto userAddCallDto= new UserAddCallDto();
        userAddCallDto.setId(id);
        userAddCallDto.setPassword(password);
        userAddCallDto.setBirth(birth);
        userAddCallDto.setUsername(username);
        userAddCallDto.setEmail(email);
        final String requestBody= objectMapper.writeValueAsString(userAddCallDto);

        ResultActions resultActions=mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("update user pw api 테스트")
    @Test
    public void updatePassword() throws Exception{
        final String url="/user/updatePassword";

        final String username="";
        final String email="";
        final String id="testId2";
        final String password="newpw525";
        final String birth="";
        final UserAddCallDto userAddCallDto= new UserAddCallDto();
        userAddCallDto.setId(id);
        userAddCallDto.setPassword(password);
        userAddCallDto.setBirth(birth);
        userAddCallDto.setUsername(username);
        userAddCallDto.setEmail(email);
        final String requestBody= objectMapper.writeValueAsString(userAddCallDto);

        ResultActions resultActions=mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("update user email api 테스트")
    @Test
    public void updateEmail() throws Exception{
        final String url="/user/updateEmail";

        final String username="";
        final String email="updateEmail2";
        final String id="testId2";
        final String password="";
        final String birth="";
        final UserAddCallDto userAddCallDto= new UserAddCallDto();
        userAddCallDto.setId(id);
        userAddCallDto.setPassword(password);
        userAddCallDto.setBirth(birth);
        userAddCallDto.setUsername(username);
        userAddCallDto.setEmail(email);
        final String requestBody= objectMapper.writeValueAsString(userAddCallDto);

        ResultActions resultActions=mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("get userinfo api 테스트")
    @Test
    public void getUserInfo() throws Exception{
        final String url="/user/getUserInfo/testId2";
        final ResultActions resultActions=mockMvc.perform(get(url,"testId2"));
        resultActions.andExpect(status().isOk()).andDo(print());
    }

}
