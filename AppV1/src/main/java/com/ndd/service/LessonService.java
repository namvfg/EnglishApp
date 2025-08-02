/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ndd.service;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public interface LessonService {
    List<Object[]> getLessons(Map<String, String> params);
}
