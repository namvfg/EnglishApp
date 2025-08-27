/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ndd.repository;

import com.ndd.pojo.Category;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Admin
 */
public interface CategoryRepository {

    List<Category> getCategories(Map<String, String> params);

    Category getCategoryById(int id);

    long countCategories(Map<String, String> params);

    Predicate[] buildCategoryPredicates(CriteriaBuilder b, Root root, Map<String, String> params);

    boolean addOrUpdateCategory(Category c);
   
    boolean deleteCategoryById(int id);
}
