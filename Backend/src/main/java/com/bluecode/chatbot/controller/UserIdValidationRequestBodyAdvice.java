package com.bluecode.chatbot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import java.lang.reflect.Type;


@Slf4j
@Component
@RestControllerAdvice
public class UserIdValidationRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        // 모든 @RequestBody에 대해 적용할 수 있도록 true 반환
        return true;
    }


    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,MethodParameter parameter,
                                Type targetType,Class<? extends HttpMessageConverter<?>> converterType){
       String userIdFromObject;
        String userIdFromJwt;
        try{
            // body 를 jsonNode 로 변환 후 userid 추출
            String bodyString = objectMapper.writeValueAsString(body);
            JsonNode jsonNode=objectMapper.readTree(bodyString);
            userIdFromObject = String.valueOf(jsonNode.get("userId"));

            userIdFromJwt= String.valueOf(getCurrentUserId());
            log.info("dto 에서 추출된 userid "+userIdFromObject+" jwt 에서 추출된 userid "+userIdFromJwt);

        } catch (Exception e){
            log.error("Body 데이터 변환 중 오류 ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청 본문 데이터 변환 오류", e);
        }

        // 두 값이 null 이 아닐때만 비교
        if(!userIdFromObject.equals("null") && !userIdFromJwt.equals("null")) {
            if(!userIdFromObject.equals(userIdFromJwt)){
                throw new AccessDeniedException("JWT 토큰 ID와 Body ID가 일치하지 않음");
            }
        }

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    // jwt에서 userid 추출
    private Long getCurrentUserId() {
        // jwt 토큰 이용하지 않은 요청은 anonymousUser 를 담고 있음
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
            return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return null;
    }

}
