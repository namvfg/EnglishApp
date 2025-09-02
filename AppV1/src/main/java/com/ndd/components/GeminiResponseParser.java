/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndd.DTO.GeminiSpeakingScoreDTO;
import com.ndd.DTO.GeminiWritingScoreDTO;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
public class GeminiResponseParser {

    @Autowired
    private ObjectMapper mapper;

    public String parseGeminiJsonToString(String geminiRawText) throws IOException {
        JsonNode root = mapper.readTree(geminiRawText);
        JsonNode textNode = root.path("candidates").get(0)
                .path("content").path("parts").get(0)
                .path("text");

        String text = textNode.asText().trim();

        // Loại bỏ ```json và ```
        if (text.startsWith("```json")) {
            text = text.replaceFirst("^```json\\s*", "");
        }
        if (text.endsWith("```")) {
            text = text.replaceFirst("\\s*```$", "");
        }
        return text;
    }

    public GeminiWritingScoreDTO parseWriting(String geminiRawText) throws IOException {
        String text = parseGeminiJsonToString(geminiRawText);
        return mapper.readValue(text, GeminiWritingScoreDTO.class);
    }

    public GeminiSpeakingScoreDTO parseSpeaking(String geminiRawText) throws IOException {
        String text = parseGeminiJsonToString(geminiRawText);
        return mapper.readValue(text, GeminiSpeakingScoreDTO.class);
    }
}
