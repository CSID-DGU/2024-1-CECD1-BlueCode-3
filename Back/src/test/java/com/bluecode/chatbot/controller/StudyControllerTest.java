package com.bluecode.chatbot.controller;


import com.bluecode.chatbot.dto.DataCallDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudyControllerTest {

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

    //유저의 커리큘럼 진행 현황 요청
    @DisplayName("findCurriculmProgress api 테스트")
    @Test
    public void findCurriculumProgress() throws Exception{
        final String url="/curriculum/chapters";

        final long user1_name= 1;
        final long curriculumId_1=0;
        final DataCallDto userDto_1=new DataCallDto();
        userDto_1.setUserId(user1_name);
        userDto_1.setCurriculumId(curriculumId_1);

        final String requestBody_1=objectMapper.writeValueAsString(userDto_1);
        ResultActions result_1= mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody_1));
        result_1.andExpect(status().isOk()).andDo(print());

        final long user2_name= 2;
        final long curriculumId_2=5;
        final DataCallDto userDto_2=new DataCallDto();
        userDto_2.setUserId(user2_name);
        userDto_2.setCurriculumId(curriculumId_2);

        final String requestBody_2=objectMapper.writeValueAsString(userDto_2);
        ResultActions result_2=mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody_2));
        result_2.andExpect(status().isOk()).andDo(print());

    }

}