/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.DTO.CategoryDTO;
import com.ndd.DTO.LessonDTO;
import com.ndd.pojo.Category;
import com.ndd.pojo.Lesson;
import com.ndd.service.CategoryService;
import com.ndd.service.LessonService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class ApiCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LessonService lessonService;

    @GetMapping("/categories")
    @CrossOrigin
    public ResponseEntity<List<CategoryDTO>> list(@RequestParam Map<String, String> params) {
        List<Category> categories = categoryService.getCategories(params);

        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build(); //204 no content
        }

        List<CategoryDTO> result = categories.stream()
                .map(c -> new CategoryDTO(c.getId(), c.getName(), c.getCreatedDate(), c.getUpdatedDate(), c.getCategoryTypeId().getId(), c.getCategoryTypeId().getName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result); // 200 ok + json
    }

    //info 1 category
    @GetMapping("/categories/{cateId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int cateId) {
        Category c = this.categoryService.getCategoryById(cateId); 

        if (c == null) {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
        CategoryDTO dto = new CategoryDTO(c.getId(), c.getName(), c.getCreatedDate(), 
                c.getUpdatedDate(), c.getCategoryTypeId().getId(), c.getCategoryTypeId().getName());
        return ResponseEntity.ok(dto); // HTTP 200 + body
    }

    @GetMapping("/categories/{cateId}/lessons")
    public ResponseEntity<List<LessonDTO>> getLessonsByCategory(@PathVariable int cateId, @RequestParam Map<String, String> params) {
        List<Lesson> lessons = this.lessonService.getLessonsByCategoryId(cateId, params);

        if (lessons.isEmpty()) {
            return ResponseEntity.noContent().build(); //204 no content
        }

        List<LessonDTO> result = lessons.stream()
                .map(l -> new LessonDTO(l.getId(), l.getTitle(), l.getImage(),
                l.getContent(), l.getCreatedDate(), l.getUpdatedDate(),
                l.getCategoryId().getId(), l.getCategoryId().getName(),
                l.getLessonTypeId().getId(), l.getLessonTypeId().getName(),
                l.getLessonTypeId().getSkill().toString()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result); // 200 ok + json
    }

    @GetMapping("/categories/{cateId}/lessons/count")
    public ResponseEntity<Long> countLessons(@PathVariable int cateId, @RequestParam Map<String, String> params) {
        long count = this.lessonService.countLessonsByCategoryId(cateId, params);
        return ResponseEntity.ok(count);
    }
}
