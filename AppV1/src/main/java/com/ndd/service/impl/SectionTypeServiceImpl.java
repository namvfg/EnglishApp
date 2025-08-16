/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.pojo.SectionType;
import com.ndd.repository.SectionTypeRepository;
import com.ndd.service.SectionTypeService;
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
public class SectionTypeServiceImpl implements SectionTypeService{
    
    @Autowired
    private SectionTypeRepository sectionTypeRepo;

    @Override
    public List<SectionType> getSectionTypes() {
        return this.sectionTypeRepo.getSectionTypes();
    }

    @Override
    public SectionType getSectionTypeById(int id) {
        return this.sectionTypeRepo.getSectionTypeById(id);
    }
    
    
    
}
