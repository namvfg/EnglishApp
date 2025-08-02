/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.pojo.CategoryType;
import com.ndd.repository.CategoryTypeRepository;
import com.ndd.service.CategoryTypeService;
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
public class CategoryTypeServiceImpl implements CategoryTypeService{

    @Autowired
    private CategoryTypeRepository categoryTypeRepo;
    
    @Override
    public List<CategoryType> getCategoryTypes(){
        return this.categoryTypeRepo.getCategoryTypes();
    }
    
}
