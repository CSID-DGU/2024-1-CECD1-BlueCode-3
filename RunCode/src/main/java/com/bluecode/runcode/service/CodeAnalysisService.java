package com.bluecode.runcode.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CodeAnalysisService {

    private static final Logger log = LoggerFactory.getLogger(RunCodeService.class);

    public int analyzePythonCode(String userCode) throws IOException, InterruptedException {
        String scriptPath = "src/main/resources/scripts/analyze_code.py";
        ProcessBuilder builder = new ProcessBuilder("python", scriptPath);
        builder.redirectErrorStream(true); // 에러 스트림과 출력 스트림을 합침
        Process process = builder.start();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8))) {
            writer.write(userCode);
            writer.flush();
        }

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n"); // 출력 내용을 줄바꿈 문자와 함께 추가
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            log.error("파이썬 스크립트 실행 에러: " + exitCode);
            throw new RuntimeException("파이썬 스크립트 실행 에러: " + exitCode);
        }

        log.info("파이썬 스크립트 출력: \n" + output.toString()); // 출력 결과 로깅

        String jsonOutput = output.toString().split("###JSON###")[1];
        JSONObject json = new JSONObject(jsonOutput.trim());
        return json.getInt("input_count");
    }
}