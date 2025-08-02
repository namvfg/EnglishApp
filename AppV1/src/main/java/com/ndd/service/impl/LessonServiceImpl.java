/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.repository.LessonRepository;
import com.ndd.service.LessonService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
@Transactional
public class LessonServiceImpl implements LessonService{

    private LessonRepository lessonRepo;
    
    @Override
    public List<Object[]> getLessons(Map<String, String> params) {
        return this.lessonRepo.getLessons(null);
    }
    
}
