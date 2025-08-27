/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ndd.service;

import com.ndd.pojo.Category;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public interface CategoryService {

    List<Category> getCategories(Map<String, String> params);

    long countCategories(Map<String, String> params);

    boolean addOrUpdateCategory(Category c);

    Category getCategoryById(int id);
    
    boolean deleteCategoryById(int id);
}
