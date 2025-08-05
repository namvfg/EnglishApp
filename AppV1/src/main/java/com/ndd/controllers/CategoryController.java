/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.enums.Skill;
import com.ndd.pojo.Category;
import com.ndd.service.CategoryService;
import com.ndd.service.LessonService;
import java.util.Date;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Admin
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private LessonService lessonService;
    
    @Autowired
    private Environment env;

    @GetMapping("/categories")
    public String list(Model model) {
        model.addAttribute("category", new Category());
        return "categories";
    }
    
    @GetMapping("/categories/{id}")
    public String update(Model model, @PathVariable(value = "id") int id, @RequestParam Map<String, String> params) {
        model.addAttribute("category", this.categoryService.getCategoryById(id));
        model.addAttribute("skills", Skill.values());
        model.addAttribute("lessons", this.lessonService.getLessonsByCategoryId(id, params));
        
        //phan trang
        long total = this.lessonService.countLessonsByCategoryId(id, params);
        long pageSize = Long.parseLong(env.getProperty("PAGE_SIZE"));
        double totalPages = Math.ceil((double) total / pageSize);
        model.addAttribute("pageQuantity", totalPages);
        return "categories";
    }

    @PostMapping("/categories")
    public String add(@ModelAttribute(value = "category") @Valid Category c, BindingResult rs) {
        if (!rs.hasErrors()) {
            Date now = new Date();
            if (c.getId() == null) {
                c.setCreatedDate(now);
                c.setUpdatedDate(now);
            } else {
                Category old = this.categoryService.getCategoryById(c.getId());
                c.setCreatedDate(old.getCreatedDate());
                c.setUpdatedDate(now);
            }

            if (this.categoryService.addOrUpdateCategory(c)) {
                return "redirect:/";
            }
        }
        return "categories";
    }
}
