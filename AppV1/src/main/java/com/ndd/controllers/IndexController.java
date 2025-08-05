/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.service.CategoryService;
import com.ndd.service.CategoryTypeService;
import com.ndd.service.LessonService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Admin
 */
@Controller
@ControllerAdvice
@PropertySource("classpath:configs.properties")
public class IndexController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryTypeService categoryTypeService;
    @Autowired
    private Environment env;

    @ModelAttribute
    public void commonAttr(Model model) {
        model.addAttribute("categoryTypes", this.categoryTypeService.getCategoryTypes());
    }

    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("categories", this.categoryService.getCategories(params));

        //phantrang
        long total = this.categoryService.countCategories(params);
        long pageSize = Long.parseLong(env.getProperty("PAGE_SIZE"));
        double totalPages = Math.ceil((double) total / pageSize);
        model.addAttribute("pageQuantity", (long) totalPages);
//        model.addAttribute("lessons", this.lessonService.getLessons(null));
        return "index";
    }
}
