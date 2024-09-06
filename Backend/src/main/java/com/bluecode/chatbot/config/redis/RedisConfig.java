package com.bluecode.chatbot.config.redis;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.password}")
    private String password;

    // Lettuce 방식으로 Redis 접속 설정
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);
        redisConfig.setPassword(password);

        return new LettuceConnectionFactory(redisConfig);
    }

    // 범용성을 위해 제네릭으로
    // 이메일 인증은 string,string 으로 key 는 이메일, value 는 인증 코드
    @Bean
    public RedisTemplate<?,?> redisTemplate(){
        RedisTemplate<byte[],byte[]> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory((redisConnectionFactory()));
        return redisTemplate;
    }

}
