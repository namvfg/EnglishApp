/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.pojo.Section;
import com.ndd.repository.SectionRepository;
import com.ndd.service.SectionService;
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
public class SectionServiceImpl implements SectionService{

    @Autowired
    private SectionRepository sectionRepo;
    
    @Override
    public List<Section> getSectionsByLessonId(int lessonId) {
        return this.sectionRepo.getSectionsByLessonId(lessonId);
    }

    @Override
    public boolean addOrUpdateSection(Section s) {
        return this.sectionRepo.addOrUpdateSection(s);
    }

    @Override
    public Section getSectionById(int id) {
        return this.sectionRepo.getSectionById(id);
    }
    
}
