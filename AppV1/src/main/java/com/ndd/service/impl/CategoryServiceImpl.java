/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.pojo.Category;
import com.ndd.repository.CategoryRepository;
import com.ndd.service.CategoryService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepo;
    
    @Override
    public List<Category> getCategories(Map<String, String> params) {
        return this.categoryRepo.getCategories(params);
    }

    @Override
    public long countCategories(Map<String, String> params) {
        return this.categoryRepo.countCategories(params);
    }

    @Override
    public boolean addOrUpdateCategory(Category c) {
        return this.categoryRepo.addOrUpdateCategory(c);
    }

    @Override
    public Category getCategoryById(int id) {
        return this.categoryRepo.getCategoryById(id);
    }

    @Override
    public boolean deleteCategoryById(int id) {
        return this.categoryRepo.deleteCategoryById(id);
    }
    
}
