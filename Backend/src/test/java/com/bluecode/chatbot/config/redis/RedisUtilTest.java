package com.bluecode.chatbot.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
@Slf4j
class RedisUtilTest {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void redisTest(){
        String email="tmp@test.com";
        String code="12534";

        redisUtil.setDataExpire(email,code,5*3L);
        Assertions.assertTrue(redisUtil.existData(email));
        Assertions.assertFalse(redisUtil.existData("notemail@gmail.com"));
        Assertions.assertEquals(redisUtil.getData(email), "12534");
    }
}