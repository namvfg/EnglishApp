/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ndd.service;

import com.ndd.pojo.Lesson;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public interface LessonService {

    List<Lesson> getLessonsByCategoryId(int categoryId, Map<String, String> params);

    long countLessonsByCategoryId(int categoryId, Map<String, String> params);

    List<Lesson> getLessons(Map<String, String> params);

    long countLessons(Map<String, String> params);

    boolean addOrUpdateLesson(Lesson l);

    Lesson getLessonById(int id);
}
