/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.DTO.PracticeEvaluateRequestDTO;
import com.ndd.DTO.PracticeSessionDTO;
import com.ndd.DTO.UserAnswerDTO;
import com.ndd.pojo.PracticeSession;
import com.ndd.pojo.UserAnswer;
import com.ndd.service.LessonService;
import com.ndd.service.PracticeSessionService;
import com.ndd.service.SectionService;
import com.ndd.service.UserService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class ApiPracticeSessionController {

    @Autowired
    private PracticeSessionService practiceSessionService;
    @Autowired
    private UserService userService;
    @Autowired
    private SectionService sectionService;
    @Autowired 
    private LessonService lessonService;

    @PostMapping("/practice/evaluate")
    public ResponseEntity<?> evaluatePractice(@RequestBody PracticeEvaluateRequestDTO request) {
        if (request.getPracticeSession() == null || request.getUserAnswers() == null || request.getUserAnswers().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Thiếu thông tin session hoặc answers"));
        }

        // Chuyển từ DTO → Entity
        PracticeSession session = new PracticeSession();
        PracticeSessionDTO sessionDTO = request.getPracticeSession();

        session.setLessonId(lessonService.getLessonById(sessionDTO.getLessonId()));
        session.setDuration(sessionDTO.getDuration());
        session.setStartAt(Timestamp.valueOf(sessionDTO.getStartAt().replace("T", " ").replace("Z", ""))); // hoặc dùng formatter ISO 8601
        session.setScore(request.getScore() != null ? request.getScore() : 0f);
        session.setUserId(userService.getUserById(request.getUserId()));

        List<UserAnswer> answers = new ArrayList<>();
        for (UserAnswerDTO dto : request.getUserAnswers()) {
            UserAnswer ua = new UserAnswer();
            ua.setSectionId(sectionService.getSectionById(dto.getSectionId()));
            ua.setAnswer(dto.getAnswer());
            ua.setIsCorrect(dto.getIsCorrect());
            ua.setScore(dto.getScore());
            ua.setCreatedDate(new Date());
            ua.setUpdatedDate(new Date());
            ua.setPracticeSessionId(session); // thiết lập khóa ngoại
            answers.add(ua);
        }

        // Gọi service lưu
        boolean success = practiceSessionService.createSessionWithAnswers(session, answers);
        if (!success) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lưu session hoặc answers thất bại"));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Lưu kết quả thành công",
                "score", session.getScore()
        ));
    }
}
