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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurriculumControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .build();
    }
    //유저가 선택한 커리큘럼의 챕터들 정보 요청
    @DisplayName("findCurriculumName api 테스트")
    @Test
    public void findCurriculunName() throws Exception{
        final String url="/curriculum/{curriculumId}";
        final ResultActions resultActions=mockMvc.perform(get(url,1)); //파이썬 커리큘럽 id(1)

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.list",hasSize(17)))  //현재 파이썬 커리큘럼에는 17개의 챕터를 가지고있음
                .andDo(print());
    }
}