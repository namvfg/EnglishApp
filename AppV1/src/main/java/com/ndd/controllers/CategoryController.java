/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.pojo.Category;
import com.ndd.service.CategoryService;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Admin
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String list(Model model) {
        model.addAttribute("category", new Category());
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
                c.setUpdatedDate(now);
            }

            if (this.categoryService.addOrUpdateCategory(c)) {
                return "redirect:/";
            }
        }
        return "categories";
    }

}
