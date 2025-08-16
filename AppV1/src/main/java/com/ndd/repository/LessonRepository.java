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

    List<Lesson> getLessonsByCategoryId(Integer categoryId, Map<String, String> params);

    Predicate[] buildLessonPredicates(CriteriaBuilder b, Root<Lesson> root, Integer categoryId, Map<String, String> params);
    
    List<Lesson> getLessons(Map<String, String> params);
            
    long countLessons(Map<String, String> params);
    
    long countLessonsByCategoryId(Integer categoryId, Map<String, String> params);
    
    boolean addOrUpdateLesson(Lesson l);
    
    Lesson getLessonById(Integer id);
}
