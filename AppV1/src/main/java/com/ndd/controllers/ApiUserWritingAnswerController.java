/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.DTO.GeminiWritingScoreDTO;
import com.ndd.pojo.UserWritingAnswer;
import com.ndd.service.GeminiService;
import com.ndd.service.LessonService;
import com.ndd.service.UserService;
import com.ndd.service.UserWritingAnswerService;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class ApiUserWritingAnswerController {

    @Autowired
    private GeminiService geminiService;
    @Autowired
    private UserService userService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private UserWritingAnswerService userWritingAnswerService;

    @PostMapping("/writing/evaluate")
    public ResponseEntity<?> evaluateWriting(
            @RequestParam int userId,
            @RequestParam int lessonId,
            @RequestParam String lessonType,
            @RequestParam String prompt,
            @RequestParam String essay
    ) {
        try {
            // 1. Gọi Gemini chấm điểm
            String json = geminiService.evaluate(lessonType, prompt, essay);
            System.out.println("Raw response from Gemini:\n" + json); //  Dòng này
            // 2. Parse JSON thành DTO
            GeminiWritingScoreDTO dto = geminiService.parseGeminiResponse(json);

            // 3. Lưu vào DB
            UserWritingAnswer answer = new UserWritingAnswer();
            answer.setUserId(userService.getUserById(userId));
            answer.setLessonId(lessonService.getLessonById(lessonId));
            answer.setContent(dto.getContent());
            answer.setFeedback(dto.getFeedback());
            answer.setTaskScore(dto.getTask_score());
            answer.setCoherenceScore(dto.getCoherence_score());
            answer.setLexicalScore(dto.getLexical_score());
            answer.setGrammarScore(dto.getGrammar_score());
            answer.setOverallScore(dto.getOverall_score());
            answer.setCreatedDate(new Date());
            answer.setUpdatedDate(new Date());

            userWritingAnswerService.addUserWritingAnswer(answer);

            return ResponseEntity.ok(Map.of(
                    "message", "Chấm điểm và lưu thành công",
                    "answerId", answer.getId()
            ));

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                    "error", ex.getMessage()
            ));
        }
    }

    @GetMapping("/writing/{id}")
    public ResponseEntity<?> getWritingResult(@PathVariable("id") int id) {
        try {
            UserWritingAnswer answer = userWritingAnswerService.getUWAById(id);

            if (answer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Không tìm thấy bài viết với ID: " + id));
            }

            GeminiWritingScoreDTO dto = new GeminiWritingScoreDTO();
            dto.setContent(answer.getContent());
            dto.setFeedback(answer.getFeedback());
            dto.setTask_score(answer.getTaskScore());
            dto.setCoherence_score(answer.getCoherenceScore());
            dto.setLexical_score(answer.getLexicalScore());
            dto.setGrammar_score(answer.getGrammarScore());
            dto.setOverall_score(answer.getOverallScore());

            return ResponseEntity.ok(dto);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi khi truy vấn bài viết: " + ex.getMessage()));
        }
    }
}
