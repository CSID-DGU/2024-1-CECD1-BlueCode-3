package com.bluecode.runcode.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.bluecode.runcode.model.RunCodeInfo;
import com.bluecode.runcode.model.UserCodeInfo;
import com.bluecode.runcode.service.RunCodeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CodeExecutionWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private RunCodeService runCodeService;
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(RunCodeService.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 받은 메시지 로그 출력
        String payload = message.getPayload();
        log.info("Received WebSocket message: {}", payload);
    
        // JSON 파싱
        JsonNode jsonNode = objectMapper.readTree(payload);
        String action = jsonNode.get("action").asText();
        log.info("Action: {}", action);
    
        // 코드 실행 요청 처리
        if ("runCode".equals(action)) {
            UserCodeInfo userCodeInfo = objectMapper.treeToValue(jsonNode.get("data"), UserCodeInfo.class);
            log.info("Executing code for language: {}", userCodeInfo.getSelectedLang());
    
            // 코드 실행
            RunCodeInfo runCodeInfo = runCodeService.executeCode(session, userCodeInfo);
    
            if (runCodeInfo.isAwaitingInput()) {
                // 입력이 필요한 경우 클라이언트에 입력 요청
                String inputRequestJson = objectMapper.writeValueAsString(
                    new RunCodeInfo("입력 요청", true)
                );
                log.info("클라이언트 입력 요청: {}", inputRequestJson);
                session.sendMessage(new TextMessage(inputRequestJson));
            } else {
                // 결과를 클라이언트에 전송
                String responseMessage = objectMapper.writeValueAsString(runCodeInfo);
                log.info("클라이언트에 응답 전송: {}", responseMessage);
                session.sendMessage(new TextMessage(responseMessage));
            }
        } else if ("input".equals(action)) {
            // 입력 처리
            String input = jsonNode.get("data").asText();
            log.info("받은 input 내용: {}", input);
    
            // input과 함께 session 객체도 전달
            RunCodeInfo runCodeInfo = runCodeService.sendInputToProcess(session.getId(), input, session);
            
            // 최종 결과를 클라이언트에 전송
            String responseMessage = objectMapper.writeValueAsString(runCodeInfo);
            log.info("최종 결과 클라이언트에 전송: {}", responseMessage);
            session.sendMessage(new TextMessage(responseMessage));
        }
    }
}