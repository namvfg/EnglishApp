/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.DTO.GeminiSpeakingScoreDTO;
import com.ndd.pojo.Lesson;
import com.ndd.pojo.UserSpeakingAnswer;
import com.ndd.service.AssemblyAIService;
import com.ndd.service.GeminiService;
import com.ndd.service.LessonService;
import com.ndd.service.R2StorageService;
import com.ndd.service.UserService;
import com.ndd.service.UserSpeakingAnswerService;
import com.ndd.utils.SpeakingUtils;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class ApiUserSpeakingAnswerController {

    @Autowired
    private GeminiService geminiService;
    @Autowired
    private UserService userService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private UserSpeakingAnswerService userSpeakingAnswerService;
    @Autowired
    private R2StorageService r2StorageService;
    @Autowired
    private AssemblyAIService assemblyAIService;

    @PostMapping("/speaking/evaluate")
    public ResponseEntity<?> evaluateSpeaking(
            @RequestParam int userId,
            @RequestParam int lessonId,
            @RequestParam("audio") MultipartFile audioFile
    ) {
        try {
            // 1. Upload file gốc lên R2
            String audioUrl = r2StorageService.uploadFile(audioFile);

            // 2. Transcribe với AssemblyAI bằng URL
            String transcript = assemblyAIService.transcribe(audioFile); // Gọi AssemblyAI

            System.out.println("Transcript: " + transcript);

            // 3. Lấy câu hỏi & chấm điểm
            Lesson lesson = lessonService.getLessonById(lessonId);
            List<String> questions = SpeakingUtils.extractQuestionsFromHtml(lesson.getContent());

            String geminiJson = geminiService.evaluateSpeaking(lesson.getTitle(), questions, transcript);
            GeminiSpeakingScoreDTO dto = geminiService.parseGeminiSpeakingResponse(geminiJson);

            // 4. Lưu kết quả
            UserSpeakingAnswer answer = new UserSpeakingAnswer();
            answer.setUserId(userService.getUserById(userId));
            answer.setLessonId(lesson);
            answer.setAudioUrl(audioUrl);
            answer.setTranscript(transcript);
            answer.setPronunciationScore(dto.getPronunciationScore());
            answer.setFluencyScore(dto.getFluencyScore());
            answer.setCoherenceScore(dto.getCoherenceScore());
            answer.setLexicalResourceScore(dto.getLexicalResourceScore());
            answer.setGrammarScore(dto.getGrammarScore());
            answer.setOverallScore(dto.getOverallScore());
            answer.setFeedback(dto.getFeedback());
            answer.setCreatedDate(new Date());
            answer.setUpdatedDate(new Date());
            userSpeakingAnswerService.addUserSpeakingAnswer(answer);

            return ResponseEntity.ok(Map.of(
                    "message", "Chấm điểm và lưu thành công",
                    "answerId", answer.getId(),
                    "audioUrl", audioUrl
            ));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/speaking/{id}")
    public ResponseEntity<?> getSpeakingResult(@PathVariable("id") int id) {
        try {
            UserSpeakingAnswer answer = userSpeakingAnswerService.getUSAById(id);
            if (answer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Không tìm thấy bài nói với ID: " + id));
            }

            GeminiSpeakingScoreDTO dto = new GeminiSpeakingScoreDTO();
            dto.setTranscript(answer.getTranscript());
            dto.setFeedback(answer.getFeedback());
            dto.setPronunciationScore(answer.getPronunciationScore());
            dto.setFluencyScore(answer.getFluencyScore());
            dto.setCoherenceScore(answer.getCoherenceScore());
            dto.setLexicalResourceScore(answer.getLexicalResourceScore());
            dto.setGrammarScore(answer.getGrammarScore());
            dto.setOverallScore(answer.getOverallScore());

            return ResponseEntity.ok(Map.of(
                    "audioUrl", answer.getAudioUrl(),
                    "score", dto
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi khi truy vấn bài nói: " + ex.getMessage()));
        }
    }

}
