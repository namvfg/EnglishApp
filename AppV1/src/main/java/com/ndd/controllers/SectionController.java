/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.DTO.SectionDTO;
import com.ndd.pojo.Category;
import com.ndd.pojo.Section;
import com.ndd.service.CategoryService;
import com.ndd.service.LessonService;
import com.ndd.service.SectionService;
import com.ndd.service.SectionTypeService;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Admin
 */
@Controller
public class SectionController {

    @GetMapping("/categories/{cateId}/lessons/{lessonId}/sections/{id}")
    public String list(Model model,
            @PathVariable(value = "cateId") int cateId,
            @PathVariable(value = "lessonId") int lessonId) {
        return "sections";
    }

}
