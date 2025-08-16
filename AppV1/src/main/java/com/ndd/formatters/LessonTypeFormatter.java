/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.formatters;

import com.ndd.pojo.CategoryType;
import com.ndd.pojo.LessonType;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Admin
 */
public class LessonTypeFormatter implements Formatter<LessonType> {

    @Override
    public String print(LessonType lessonType, Locale locale) {
        return String.valueOf(lessonType.getId());
    }

    @Override
    public LessonType parse(String lessonTypeId, Locale locale) throws ParseException {
        int id = Integer.parseInt(lessonTypeId);
        return new LessonType(id);
    }
}
