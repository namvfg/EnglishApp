/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.pojo.Lesson;
import com.ndd.repository.LessonRepository;
import com.ndd.service.LessonService;
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
public class LessonServiceImpl implements LessonService{

    @Autowired
    private LessonRepository lessonRepo;
    
    @Override
    public List<Lesson> getLessonsByCategoryId(int categoryId, Map<String, String> params) {
        return this.lessonRepo.getLessonsByCategoryId(categoryId, params);
    }

    @Override
    public long countLessonsByCategoryId(int categoryId, Map<String, String> params) {
        return this.lessonRepo.countLessonsByCategoryId(categoryId, params);
    }
    
}
