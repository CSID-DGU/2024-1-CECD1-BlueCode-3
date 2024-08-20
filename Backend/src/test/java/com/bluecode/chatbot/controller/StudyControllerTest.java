package com.bluecode.chatbot.controller;


import com.bluecode.chatbot.domain.LevelType;
import com.bluecode.chatbot.dto.CurriculumTextCallDto;
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
        final long curriculumId_1=3;
        final DataCallDto userDto_1=new DataCallDto();
        userDto_1.setUserId(user1_name);
        userDto_1.setCurriculumId(curriculumId_1);

        final String requestBody_1=objectMapper.writeValueAsString(userDto_1);
        ResultActions result_1= mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody_1));
        result_1.andExpect(status().isOk()).andDo(print());

        final long user2_name= 2;
        final long curriculumId_2=4;
        final DataCallDto userDto_2=new DataCallDto();
        userDto_2.setUserId(user2_name);
        userDto_2.setCurriculumId(curriculumId_2);

        final String requestBody_2=objectMapper.writeValueAsString(userDto_2);
        ResultActions result_2=mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody_2));
        result_2.andExpect(status().isOk()).andDo(print());

    }

    //초기 테스트 중테스트 통과한 챕터의 내용 생성 후 호출 테스트
    @DisplayName("학습 텍스트 생성 후 호출 테스트")
    @Test
    public void createText() throws Exception{
        final String url="/curriculum/curriculumcreate";

        final long curriculum_Id=11;
        final long user_Id= 2;
        final CurriculumTextCallDto curriculumTextCallDto=new CurriculumTextCallDto();
        curriculumTextCallDto.setCurriculumId(curriculum_Id);
        curriculumTextCallDto.setUserId(user_Id);

        final String reqeustBody=objectMapper.writeValueAsString(curriculumTextCallDto);
        ResultActions resultActions=mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(reqeustBody));
        resultActions.andExpect(status().isOk()).andDo(print());

        final String url_2="/curriculum/chaptertext";

        resultActions=mockMvc.perform(post(url_2)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(reqeustBody));
        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("기존 학습 텍스트 호출 테스트")
    @Test
    public void callCurriculumText() throws Exception{
        final String url="/curriculum/chaptertext";
        final long curriculum_Id=7;
        final long user_Id= 2;
        final CurriculumTextCallDto curriculumTextCallDto=new CurriculumTextCallDto();
        curriculumTextCallDto.setCurriculumId(curriculum_Id);
        curriculumTextCallDto.setUserId(user_Id);

        final String reqeustBody=objectMapper.writeValueAsString(curriculumTextCallDto);
        ResultActions resultActions=mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(reqeustBody));
        resultActions.andExpect(status().isOk()).andDo(print());

    }

}