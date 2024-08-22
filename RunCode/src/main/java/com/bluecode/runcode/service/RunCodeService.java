package com.bluecode.runcode.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.bluecode.runcode.model.RunCodeInfo;
import com.bluecode.runcode.model.UserCodeInfo;

@Service
public class RunCodeService {

    // 세션 ID와 해당 세션의 프로세스를 매핑하는 Map
    private Map<String, Process> sessionProcessMap = new ConcurrentHashMap<>();
    private Map<String, String> sessionCodeMap = new ConcurrentHashMap<>();
    private Map<String, Integer> sessionInputCountMap = new ConcurrentHashMap<>();
    private Map<String, Integer> sessionProcessedInputCountMap = new ConcurrentHashMap<>();
    private Map<String, String> sessionLanguageMap = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(RunCodeService.class);

    public RunCodeInfo executeCode(WebSocketSession session, UserCodeInfo userCodeInfo) {
        String language = userCodeInfo.getSelectedLang();
        String code = userCodeInfo.getCodeText();
        String sessionId = session.getId();
    
        int inputCount = countInputFunctions(language, code); // 입력 함수의 개수를 계산하여 저장
        sessionCodeMap.put(sessionId, code);  // 코드를 세션에 저장
        sessionLanguageMap.put(sessionId, language);  // 언어 정보를 세션에 저장
        sessionInputCountMap.put(sessionId, inputCount);
        sessionProcessedInputCountMap.put(sessionId, 0);

        // 입력 함수가 있다면 대기 상태로 반환
        if (inputCount > 0) {
            Process process = startProcess(code, language);
            sessionProcessMap.put(sessionId, process);
            return new RunCodeInfo("입력 대기 중", true);
        } else {
            try {
                return buildAndExecuteCode(session, code, language, sessionId);
            } catch (IOException e) {
                log.error("코드 실행 실패: {}", e.getMessage());
                return new RunCodeInfo("코드 실행 실패: " + e.getMessage());
            }
        }
    }

    private int countInputFunctions(String language, String code) {
        int count = 0;
        switch (language.toLowerCase()) {
            case "python":
                count = countOccurrences(code, "input(");
                break;
            case "java":
                count = countOccurrences(code, "nextLine()") + countOccurrences(code, "nextInt()");
                break;
            case "c":
            case "c++":
                count = countOccurrences(code, "scanf(") + countOccurrences(code, "cin >>");
                break;
            default:
                throw new IllegalArgumentException("지원하지 않는 언어: " + language);
        }
        return count;
    }

    private int countOccurrences(String text, String substring) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length();
        }
        return count;
    }

    // 입력을 처리하고 빌드를 실행
    public RunCodeInfo sendInputToProcess(String sessionId, String input, WebSocketSession session) throws IOException {
        Process process = sessionProcessMap.get(sessionId);
        if (process != null && process.isAlive()) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            log.info("프로세스에 전달할 input: {}", input);
            writer.write(input);
            writer.newLine();
            writer.flush();
            log.info("프로세스에 input 전달 완료");
    
            int processedInputCount = sessionProcessedInputCountMap.getOrDefault(sessionId, 0) + 1;
            sessionProcessedInputCountMap.put(sessionId, processedInputCount);
            int expectedInputCount = sessionInputCountMap.getOrDefault(sessionId, 0);
    
            log.info("processedInputCount: {}, expectedInputCount: {}", processedInputCount, expectedInputCount);
            if (processedInputCount < expectedInputCount) {
                return new RunCodeInfo("추가 입력 대기 중", true);
            } else {
                return handleProcessOutput(session, process, sessionLanguageMap.get(sessionId));
            }
        } else {
            return new RunCodeInfo("프로세스가 종료되었습니다.");
        }
    }

    // 프로세스 시작(단, 실행은 보류)
    private Process startProcess(String code, String language) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            setupProcessBuilder(processBuilder, language, code);
            log.info("프로세스 시작(실행 대기)");
            return processBuilder.start();
        } catch (IOException e) {
            log.error("프로세스 시작 중 오류 발생: {}", e.getMessage());
            return null;  // 또는 적절한 예외 처리 로직 구현
        }
    }

    // 프로세스 빌드 및 실행
    private RunCodeInfo buildAndExecuteCode(WebSocketSession session, String code, String language, String sessionId) throws IOException {
        try{
            ProcessBuilder processBuilder = new ProcessBuilder();
            setupProcessBuilder(processBuilder, language, code);
            Process process = processBuilder.start();
            sessionProcessMap.put(sessionId, process);
            log.info("프로세스 빌드 및 실행");
            return handleProcessOutput(session, process, language);
        } catch (IOException e) {
            log.error("프로세스 시작 및 빌드 중 오류 발생: {}", e.getMessage());
            return null;
        }
    }

    // 프로세스의 출력을 처리하고 웹소켓을 통해 클라이언트에 전송
    private RunCodeInfo handleProcessOutput(WebSocketSession session, Process process, String language) throws IOException {
        StringBuilder outputBuilder = new StringBuilder();
        StringBuilder errorBuilder = new StringBuilder();
    
        log.info("프로세스 출력 시작");
        log.info("받은 정보 - session: {}, process: {}, language: {}", session, process, language);
    
        try {
            int exitCode = process.waitFor(); // 프로세스 종료 대기
            log.info("프로세스 종료 코드: {}", exitCode);
    
            if (exitCode == 0) {
                try (BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                     BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = outputReader.readLine()) != null) {
                        outputBuilder.append(line).append("\n");
                        log.info("프로세스 출력: {}", line);
                    }
                    while ((line = errorReader.readLine()) != null) {
                        errorBuilder.append(line).append("\n");
                        log.error("프로세스 에러 출력: {}", line);
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("프로세스 대기 중 인터럽트 발생: {}", e.getMessage());
            return new RunCodeInfo("프로세스 실행 중 인터럽트 발생");
        } catch (IOException e) {
            log.error("출력 처리 실패: {}", e.getMessage());
            return new RunCodeInfo("출력 처리 중 오류 발생: " + e.getMessage());
        }
    
        if (errorBuilder.length() > 0) {
            return new RunCodeInfo("Error: " + errorBuilder.toString());
        }
    
        log.info("최종 출력: {}", outputBuilder.toString());
        cleanUp(session.getId()); // 세션 종료
        return new RunCodeInfo(outputBuilder.toString());
    }    

    // 프로세스 빌더 설정
    private void setupProcessBuilder(ProcessBuilder processBuilder, String language, String code) throws IOException {
        Path filePath;
        log.info("프로세스 세팅 - 언어: {}, 코드 텍스트: {}", language, code);
        switch (language.toLowerCase()) {
            case "python":
                filePath = Files.createTempFile("script", ".py");
                Files.write(filePath, code.getBytes(StandardCharsets.UTF_8));
                processBuilder.command("python", filePath.toString());
                break;

            case "javascript":
                processBuilder.command("node", "-e", code);
                break;
            
            case "java":
                // 임시 디렉터리 생성
                Path tempDir = Files.createTempDirectory("java_compile");

                // Main.java 파일 생성
                filePath = tempDir.resolve("Main.java");
                Files.write(filePath, code.getBytes(StandardCharsets.UTF_8));

                // 컴파일러 설정
                ProcessBuilder compileJava = new ProcessBuilder("javac", filePath.toString());
                Process compileProcess = compileJava.start();

                try {
                    // 컴파일 성공 검사
                    if (compileProcess.waitFor() == 0) {
                        processBuilder.command("java", "-cp", tempDir.toString(), "Main");
                    } else {
                        // 컴파일 에러 처리
                        logErrorOutput(compileProcess);
                        throw new IOException("Java 컴파일 실패");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 스레드의 인터럽트 상태를 재설정
                    throw new IOException("컴파일 중 인터럽트 발생", e);
                }
                break;

            case "c":
                // main.c 파일 생성
                filePath = Files.createTempFile("main", ".c");
                Files.write(filePath, code.getBytes(StandardCharsets.UTF_8));

                // 컴파일러 설정
                ProcessBuilder compileC = new ProcessBuilder("gcc", filePath.toString(), "-o", filePath.getParent().resolve("main").toString());
                Process compileCProcess = compileC.start();
                try {
                    // 컴파일 성공 검사           
                    if (compileCProcess.waitFor() == 0) {
                        processBuilder.command(filePath.getParent().resolve("main").toString());
                    } else {
                        // 컴파일 에러 처리
                        logErrorOutput(compileCProcess);
                        throw new IOException("C 컴파일 실패");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 스레드의 인터럽트 상태를 재설정
                    throw new IOException("C 컴파일 중 인터럽트 발생", e);
                }
                break;
            
            case "c++":
                // main.cpp 파일 생성
                filePath = Files.createTempFile("main", ".cpp");
                Files.write(filePath, code.getBytes(StandardCharsets.UTF_8));

                // 컴파일러 설정
                ProcessBuilder compileCpp = new ProcessBuilder("g++", filePath.toString(), "-o", filePath.getParent().resolve("main").toString());
                Process compileCppProcess = compileCpp.start();
                try {
                    // 컴파일 성공 검사 
                    if (compileCppProcess.waitFor() == 0) {
                        processBuilder.command(filePath.getParent().resolve("main").toString());
                    } else {
                        // 컴파일 에러 처리
                        logErrorOutput(compileCppProcess);
                        throw new IOException("C++ 컴파일 실패");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 스레드의 인터럽트 상태를 재설정
                    throw new IOException("C++ 컴파일 중 인터럽트 발생", e);
                }
                break;
            
            default:
                throw new IllegalArgumentException("지원하지 않는 언어: " + language);
        }
    }

    // 리소스 삭제
    private void cleanUp(String sessionId) {
        Process process = sessionProcessMap.remove(sessionId);
        sessionCodeMap.remove(sessionId);
        sessionInputCountMap.remove(sessionId);
        sessionProcessedInputCountMap.remove(sessionId);
        sessionLanguageMap.remove(sessionId);
        if (process != null) {
            process.destroy();
        }
    }

    // 출력 에러 로그용
    private void logErrorOutput(Process process) throws IOException {
        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            StringBuilder errorOutput = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }
            if (!errorOutput.toString().isEmpty()) {
                log.error("컴파일 에러 출력: {}", errorOutput.toString());
            }
        }
    }
}