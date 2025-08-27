/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.DTO.LessonTypeDTO;
import com.ndd.enums.Skill;
import com.ndd.pojo.LessonType;
import com.ndd.service.LessonTypeService;
import java.util.List;
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
public class ApiLessonTypeController {
    
    @Autowired
    private LessonTypeService lessonTypeService;
    
    @GetMapping("/lesson-types")
    public ResponseEntity<List<LessonTypeDTO>> getLessonTypesBySkill(@RequestParam String skill) {
        try {
            Skill skillEnum = Skill.valueOf(skill.toLowerCase());
            List<LessonType> types = lessonTypeService.getLessonTypes(skillEnum);

            if (types.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204
            }

            List<LessonTypeDTO> result = types.stream()
                    .map(t -> new LessonTypeDTO(t.getId(), t.getName()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(result); // 200
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400
        }
    }
}
