/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.service.AssemblyAIService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@Service
public class AssemblyAIServiceImpl implements AssemblyAIService {

    @Value("${assemblyai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String transcribe(MultipartFile audioFile) throws Exception {
        // 1. Upload audio
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", apiKey);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpEntity<byte[]> audioEntity = new HttpEntity<>(audioFile.getBytes(), headers);

        ResponseEntity<Map> uploadRes = restTemplate.exchange(
                "https://api.assemblyai.com/v2/upload",
                HttpMethod.POST,
                audioEntity,
                Map.class
        );
        String uploadUrl = (String) uploadRes.getBody().get("upload_url");

        // 2. Request transcription
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.set("authorization", apiKey);
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = Map.of("audio_url", uploadUrl);
        HttpEntity<Map<String, String>> transcribeReq = new HttpEntity<>(requestBody, jsonHeaders);

        ResponseEntity<Map> transcribeRes = restTemplate.postForEntity(
                "https://api.assemblyai.com/v2/transcript",
                transcribeReq,
                Map.class
        );

        String transcriptId = (String) transcribeRes.getBody().get("id");

        // 3. Poll result
        String status = "";
        String transcriptText = "";
        while (true) {
            ResponseEntity<Map> pollRes = restTemplate.exchange(
                    "https://api.assemblyai.com/v2/transcript/" + transcriptId,
                    HttpMethod.GET,
                    new HttpEntity<>(jsonHeaders),
                    Map.class
            );
            Map body = pollRes.getBody();
            status = (String) body.get("status");
            if ("completed".equals(status)) {
                transcriptText = (String) body.get("text");
                break;
            } else if ("error".equals(status)) {
                throw new Exception("AssemblyAI transcription failed: " + body.get("error"));
            }
            Thread.sleep(2000); // đợi 2s rồi check lại
        }

        return transcriptText;
    }

}
