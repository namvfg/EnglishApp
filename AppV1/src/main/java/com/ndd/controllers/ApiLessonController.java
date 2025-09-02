/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.DTO.LessonDTO;
import com.ndd.DTO.SectionDTO;
import com.ndd.DTO.SpeakingLessonDTO;
import com.ndd.pojo.Lesson;
import com.ndd.pojo.Section;
import com.ndd.service.LessonService;
import com.ndd.service.SectionService;
import com.ndd.utils.SpeakingUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class ApiLessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/lessons")
    public ResponseEntity<List<LessonDTO>> list(@RequestParam Map<String, String> params) {
        List<Lesson> lessons = lessonService.getLessons(params);

        if (lessons.isEmpty()) {
            return ResponseEntity.noContent().build(); //204 no content
        }

        List<LessonDTO> result = lessons.stream()
                .map(l -> new LessonDTO(
                l.getId(), l.getTitle(), l.getImage(),
                l.getContent(), l.getCreatedDate(), l.getUpdatedDate(),
                l.getCategoryId().getId(), l.getCategoryId().getName(),
                l.getLessonTypeId().getId(), l.getLessonTypeId().getName(),
                l.getLessonTypeId().getSkill().toString()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result); // 200 ok + json
    }

    //count lessons
    @GetMapping("/lessons/count")
    public ResponseEntity<Long> countLessons(@RequestParam Map<String, String> params) {
        long count = this.lessonService.countLessons(params);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/lessons/{lessonId}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable int lessonId) {
        Lesson l = lessonService.getLessonById(lessonId);

        if (l == null) {
            return ResponseEntity.notFound().build(); //404 not found     
        }

        LessonDTO result = new LessonDTO(
                l.getId(), l.getTitle(), l.getImage(),
                l.getContent(), l.getCreatedDate(), l.getUpdatedDate(),
                l.getCategoryId().getId(), l.getCategoryId().getName(),
                l.getLessonTypeId().getId(), l.getLessonTypeId().getName(),
                l.getLessonTypeId().getSkill().toString());

        return ResponseEntity.ok(result); // 200 OK + json
    }

    @GetMapping("/speaking-lessons/{lessonId}")
    public ResponseEntity<SpeakingLessonDTO> getSpeakingLessonById(@PathVariable int lessonId) {
        Lesson l = lessonService.getLessonById(lessonId);

        if (l == null) {
            return ResponseEntity.notFound().build(); // 404 not found
        }
        
        List<String> questions;
        if ("Speaking Part 2".equalsIgnoreCase(l.getLessonTypeId().getName()))
            questions = List.of();
        else 
            questions = SpeakingUtils.extractQuestionsFromHtml(l.getContent());
        String introduction;
        if ("Speaking Part 1".equalsIgnoreCase(l.getLessonTypeId().getName())) {
            introduction = "This is speaking part 1. "
                    + "I'm gonna ask you about some questions about " + l.getTitle() + ". "
                    + "Are you ready?";
        } else if ("Speaking Part 2".equalsIgnoreCase(l.getLessonTypeId().getName())) {
            introduction = "This is speaking part 2."
                    + "Im gonna give you a cue card that contains your part 2 question. "
                    + "You will have 1 minute to prepare for you answer and about two minutes after that to present."
                    + "Are you ready?";
        } else if ("Speaking Part 3".equalsIgnoreCase(l.getLessonTypeId().getName())) {
            introduction = "This is speaking part 3. "
                    + "I'm gonna ask you about some questions about " + l.getTitle() + ". "
                    + "Are you ready?";
        } else {
            return ResponseEntity.badRequest().build();
        }
        
        SpeakingLessonDTO result = new SpeakingLessonDTO(l.getId(), l.getTitle(), l.getImage(), questions, introduction);
        return ResponseEntity.ok(result); //200 OK + json
    }
}
