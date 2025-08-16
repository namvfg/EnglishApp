/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.enums.Skill;
import com.ndd.pojo.LessonType;
import com.ndd.repository.LessonTypeRepository;
import com.ndd.service.LessonTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
@Transactional
public class LessonTypeServiceImpl implements LessonTypeService{

    @Autowired
    private LessonTypeRepository lessonTypeRepository;
    
    @Override
    public List<LessonType> getLessonTypes(Skill skill) {
        return this.lessonTypeRepository.getLessonTypes(skill);
    }
    
}
