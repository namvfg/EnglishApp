/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Admin
 */
public class GeminiClient {

    private static final String GEMINI_URL
            = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

    @Autowired
    private OkHttpClient httpClient;
    @Autowired
    private ObjectMapper mapper;
    private final String apiKey;

    public GeminiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public String evaluateTask1Essay(String essayContent, String imageUrl) throws IOException {
        String prompt = "You are an IELTS examiner.\n"
                + "Please evaluate the following Writing Task 1 report using IELTS criteria:\n"
                + "- Task Achievement\n"
                + "- Coherence & Cohesion\n"
                + "- Lexical Resource\n"
                + "- Grammatical Range & Accuracy\n\n"
                + "You will be provided with the prompt image and the student's response.\n\n"
                + "Return a valid JSON with fields:\n"
                + "content, feedback, task_score, coherence_score, lexical_score, grammar_score, overall_score.\n"
                + "Band scores should be float numbers from 0.0 to 9.0.\n\n"
                + "**The 'feedback' field must be a single string (not an object), containing all the evaluation content, clearly separated by headings and newlines.**\n"
                + "**All feedback content should be written in Vietnamese. However, keep section headings (e.g., 'Task Achievement', 'Coherence & Cohesion', etc.) and any example vocabulary suggestions in English.**\n"
                + "Do NOT return markdown or code formatting inside the 'feedback' string.";

        // B1: Download image → encode base64
        HttpURLConnection conn = (HttpURLConnection) new URL(imageUrl).openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        String mimeType = conn.getContentType();
        InputStream in = conn.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        in.transferTo(bos);
        String b64 = Base64.getEncoder().encodeToString(bos.toByteArray());

        // B2: Tạo JSON request
        ObjectNode root = mapper.createObjectNode();
        ArrayNode contents = mapper.createArrayNode();
        root.set("contents", contents);

        // Part 1: prompt + image
        ArrayNode parts1 = mapper.createArrayNode();
        parts1.add(obj("text", prompt));

        ObjectNode inlineData = mapper.createObjectNode();
        inlineData.put("mime_type", mimeType != null ? mimeType : "image/jpeg");
        inlineData.put("data", b64);

        ObjectNode imagePart = mapper.createObjectNode();
        imagePart.set("inline_data", inlineData);
        parts1.add(imagePart);

        ObjectNode turn1 = mapper.createObjectNode();
        turn1.put("role", "user");
        turn1.set("parts", parts1);
        contents.add(turn1);

        // Part 2: Essay
        ArrayNode parts2 = arr(obj("text", "Essay:\n" + essayContent));
        ObjectNode turn2 = mapper.createObjectNode();
        turn2.put("role", "user");
        turn2.set("parts", parts2);
        contents.add(turn2);

        // B3: Gửi request
        HttpUrl url = HttpUrl.parse(GEMINI_URL).newBuilder()
                .addQueryParameter("key", apiKey)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(
                        mapper.writeValueAsString(root),
                        MediaType.parse("application/json")))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String err = response.body() != null ? response.body().string() : "null";
                throw new IOException("Gemini API error: " + response.code() + " - " + err);
            }
            return response.body().string();
        }
    }

    public String evaluateTask2Essay(String essayContent) throws IOException {
        String prompt = "You are an IELTS examiner.\n"
                + "Please evaluate the following essay using IELTS Writing Task 2 criteria:\n"
                + "- Task Response\n"
                + "- Coherence & Cohesion\n"
                + "- Lexical Resource\n"
                + "- Grammar Range & Accuracy\n\n"
                + "Return a valid JSON with fields:\n"
                + "content, feedback, task_score, coherence_score, lexical_score, grammar_score, overall_score.\n"
                + "Band scores should be float numbers from 0.0 to 9.0.\n\n"
                + "**The 'feedback' field must be a single string (not an object), containing all the evaluation content, clearly separated by headings and newlines.**\n"
                + "**All feedback content should be written in Vietnamese. However, keep section headings (e.g., 'Task Response', 'Lexical Resource', etc.) and any example vocabulary suggestions in English.**\n"
                + "Do NOT return markdown or code formatting inside the 'feedback' string.";

        ObjectNode root = mapper.createObjectNode();
        ArrayNode contents = mapper.createArrayNode();
        root.set("contents", contents);

        // Prompt
        ArrayNode parts1 = arr(obj("text", prompt));
        ObjectNode turn1 = mapper.createObjectNode();
        turn1.put("role", "user");
        turn1.set("parts", parts1);
        contents.add(turn1);

        // Essay
        ArrayNode parts2 = arr(obj("text", "Essay:\n" + essayContent));
        ObjectNode turn2 = mapper.createObjectNode();
        turn2.put("role", "user");
        turn2.set("parts", parts2);
        contents.add(turn2);

        HttpUrl url = HttpUrl.parse(GEMINI_URL).newBuilder()
                .addQueryParameter("key", apiKey)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(
                        mapper.writeValueAsString(root),
                        MediaType.parse("application/json")))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String err = response.body() != null ? response.body().string() : "null";
                throw new IOException("Gemini API error: " + response.code() + " - " + err);
            }
            return response.body().string();
        }
    }

    // Helper methods
    private ObjectNode obj(String key, String value) {
        ObjectNode o = mapper.createObjectNode();
        o.put(key, value);
        return o;
    }

    private ObjectNode obj(String key, JsonNode value) {
        ObjectNode o = mapper.createObjectNode();
        o.set(key, value);
        return o;
    }

    private ArrayNode arr(JsonNode... nodes) {
        ArrayNode a = mapper.createArrayNode();
        for (JsonNode n : nodes) {
            a.add(n);
        }
        return a;
    }
}
