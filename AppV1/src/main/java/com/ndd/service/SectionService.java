/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ndd.service;

import com.ndd.pojo.Section;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface SectionService {
    List<Section> getSectionsByLessonId(int lessonId);
    
    boolean addOrUpdateSection(Section s);
    
    Section getSectionById(int id);
}
