/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ndd.repository;

import com.ndd.pojo.Lesson;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Admin
 */
public interface LessonRepository {

    List<Lesson> getLessonsByCategoryId(int categoryId, Map<String, String> params);

    Predicate[] buildLessonPredicates(CriteriaBuilder b, Root<Lesson> root, int categoryId, Map<String, String> params);
    
    long countLessonsByCategoryId(int categoryId, Map<String, String> params);
}
