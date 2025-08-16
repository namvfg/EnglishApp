/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.DTO.CategoryTypeDTO;
import com.ndd.pojo.CategoryType;
import com.ndd.service.CategoryTypeService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class ApiCategoryTypeController {
    
    @Autowired
    CategoryTypeService categoryTypeService;
    
    @GetMapping("/category-types")
    public ResponseEntity<List<CategoryTypeDTO>> list() {
        List<CategoryType> categoryTypes = categoryTypeService.getCategoryTypes();
        
        if (categoryTypes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        
        List<CategoryTypeDTO> result = categoryTypes.stream()
                .map(ct -> new CategoryTypeDTO(ct.getId(), ct.getName()))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(result); // 200 OK + json
    }
}
