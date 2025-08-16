/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ndd.repository;

import com.ndd.enums.Skill;
import com.ndd.pojo.LessonType;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface LessonTypeRepository {
    List<LessonType> getLessonTypes(Skill skill);
}
