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
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private SectionTypeService sectionTypeService;
    
    @Autowired
    private SectionService sectionService;
    
    @Autowired
    private LessonService lessonService;
    
    
    @PostMapping("/sections")
    public ResponseEntity<SectionDTO> create(@RequestBody @Valid SectionDTO req) {
        var sectionType = this.sectionTypeService.getSectionTypeById(req.getSectionTypeId());
        if (sectionType == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SectionType không tồn tại");
        }

        var lesson = this.lessonService.getLessonById(req.getLessonId());
        if (lesson == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lesson không tồn tại");
        }

        var s = new Section();
        s.setSectionTypeId(sectionType);
        s.setContent(req.getContent());
        s.setLessonId(lesson);

        Date now = new Date();
        s.setCreatedDate(now);
        s.setUpdatedDate(now);

        boolean ok = sectionService.addOrUpdateSection(s);
        if (!ok) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Không lưu được section");
        }

        return ResponseEntity.ok(new SectionDTO(s.getId(), s.getSectionTypeId().getId(), s.getSectionTypeId().getName(), s.getLessonId().getId(), s.getContent()));
    }

    @PutMapping("/sections/{id}")
    public ResponseEntity<SectionDTO> update(@PathVariable int id, @RequestBody @Valid SectionDTO req) {
        var s = this.sectionService.getSectionById(id);
        if (s == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Section không tồn tại");
        }

        var sectionType = this.sectionTypeService.getSectionTypeById(req.getSectionTypeId());
        if (sectionType == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SectionType không tồn tại");
        }

        s.setSectionTypeId(sectionType);
        s.setContent(req.getContent());
        Date now = new Date();        
        s.setUpdatedDate(now);

        boolean ok = this.sectionService.addOrUpdateSection(s);
        if (!ok) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Không lưu được section");
        }

        return ResponseEntity.ok(new SectionDTO(s.getId(), s.getSectionTypeId().getId(), s.getSectionTypeId().getName(), s.getLessonId().getId(), s.getContent()));
    }

}
