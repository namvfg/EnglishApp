/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.DTO.SectionDTO;
import com.ndd.pojo.Section;
import com.ndd.service.LessonService;
import com.ndd.service.SectionService;
import com.ndd.service.SectionTypeService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class ApiSectionController {

    @Autowired
    private SectionService sectionService;


    @GetMapping("/lessons/{lessonId}/sections")
    public ResponseEntity<?> getSectionsByLessonId(@PathVariable("lessonId") int lessonId) {
        List<Section> sections = this.sectionService.getSectionsByLessonId(lessonId);

        if (sections == null || sections.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<SectionDTO> result = new ArrayList<>();
        for (Section s : sections) {
            result.add(new SectionDTO(
                    s.getId(),
                    s.getContent(),
                    s.getQuestion(),
                    s.getAnswer(),
                    s.getCorrectAnswer(),
                    s.getSectionTypeId().getName(),
                    s.getSectionTypeId().getSaveType().toString()
            ));
        }
        return ResponseEntity.ok(result);
    }
}
