/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.service.WhisperService;
import java.io.File;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Admin
 */
@Service
public class WhisperServiceImpl implements WhisperService {

    private static final String WHISPER_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";

    @Autowired
    private HttpHeaders openAIHeaders;  // từ OpenAIConfig

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String transcribe(File audioFile) throws Exception {
        // Tạo body multipart
        FileSystemResource resource = new FileSystemResource(audioFile);

        HttpHeaders multipartHeaders = new HttpHeaders();
        multipartHeaders.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);
        multipartHeaders.setBearerAuth(openAIHeaders.getFirst("Authorization").replace("Bearer ", ""));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);
        body.add("model", "whisper-1");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, multipartHeaders);

        // Gửi request
        ResponseEntity<Map> response = restTemplate.postForEntity(WHISPER_ENDPOINT, requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return (String) response.getBody().get("text");
        } else {
            throw new RuntimeException("Whisper API lỗi: " + response.getStatusCode());
        }
    }

}
