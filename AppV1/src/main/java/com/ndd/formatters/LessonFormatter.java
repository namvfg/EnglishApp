/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.formatters;

import com.ndd.pojo.Lesson;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Admin
 */
public class LessonFormatter implements Formatter<Lesson>{

    @Override
    public String print(Lesson lesson, Locale locale) {
        return String.valueOf(lesson.getId());
    }

    @Override
    public Lesson parse(String lessonId, Locale locale) throws ParseException {
        int id = Integer.parseInt(lessonId);
        return new Lesson(id);
    }
    
}
