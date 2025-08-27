/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndd.DTO.GeminiWritingScoreDTO;
import com.ndd.ai.GeminiClient;
import com.ndd.components.GeminiResponseParser;
import com.ndd.service.GeminiService;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class GeminiServiceImpl implements GeminiService {

    @Autowired
    private GeminiClient geminiClient;

    @Autowired
    private GeminiResponseParser geminiResponseParser;

    @Override
    public String evaluate(String lessonTypeName, String promptContentHtml, String essayText) throws Exception {
        Document doc = Jsoup.parse(promptContentHtml);

        if ("Writing Task 1".equalsIgnoreCase(lessonTypeName)) {
            // 1. Lấy ảnh từ <img>
            Element img = doc.selectFirst("img");
            if (img == null) {
                throw new IllegalArgumentException("Đề Task 1 phải có ảnh!");
            }

            String imageUrl = img.attr("src");

            // 2. Xoá <img> để giữ lại phần đề bài là văn bản
            img.remove();
            String promptText = doc.body().text(); // Hoặc giữ lại HTML nếu muốn giữ định dạng

            // 3. Gộp đề bài + bài viết
            String fullEssay = "Prompt:\n" + promptText + "\n\nEssay:\n" + essayText;

            // 4. Gửi ảnh + bài viết tới Gemini
            return geminiClient.evaluateTask1Essay(fullEssay, imageUrl);
        } else if ("Writing Task 2".equalsIgnoreCase(lessonTypeName)) {
            String promptText = doc.body().text();
            String fullEssay = "Prompt:\n" + promptText + "\n\nEssay:\n" + essayText;
            return geminiClient.evaluateTask2Essay(fullEssay);
        } else {
            throw new IllegalArgumentException("Không hỗ trợ lessonType: " + lessonTypeName);
        }
    }

    @Override
    public GeminiWritingScoreDTO parseGeminiResponse(String json) throws IOException {
        return geminiResponseParser.parse(json);
    }

}
