/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ndd.service;

import com.ndd.enums.Skill;
import com.ndd.pojo.LessonType;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface LessonTypeService {
    List<LessonType> getLessonTypes(Skill skill);
}
