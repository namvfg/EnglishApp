/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.DTO.CategoryDTO;
import com.ndd.DTO.LessonDTO;
import com.ndd.pojo.Category;
import com.ndd.pojo.Lesson;
import com.ndd.service.LessonService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
                .map(l -> new LessonDTO(l.getId(), l.getTitle(), l.getImage(), 
                        l.getContent(), l.getCreatedDate(), l.getUpdatedDate(), 
                        l.getCategoryId().getId(), l.getCategoryId().getName(), 
                        l.getLessonTypeId().getId(), l.getLessonTypeId().getName()))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(result); // 200 ok + json
    }
    
    //count lessons
    @GetMapping("/lessons/count")
    public ResponseEntity<Long> countLessons(@RequestParam Map<String, String> params) {
        long count = this.lessonService.countLessons(params);
        return ResponseEntity.ok(count);
    }
    
}
