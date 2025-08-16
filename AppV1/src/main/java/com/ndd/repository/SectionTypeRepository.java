/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ndd.repository;

import com.ndd.pojo.SectionType;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface SectionTypeRepository {
    List<SectionType> getSectionTypes();
    
    SectionType getSectionTypeById(int id);
}
