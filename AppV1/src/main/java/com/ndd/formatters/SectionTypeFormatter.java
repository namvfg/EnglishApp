/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.formatters;

import com.ndd.pojo.SectionType;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Admin
 */
public class SectionTypeFormatter implements Formatter<SectionType> {

    @Override
    public String print(SectionType sectionType, Locale locale) {
        return String.valueOf(sectionType.getId());
    }

    @Override
    public SectionType parse(String sectionTypeId, Locale locale) throws ParseException {
        int id = Integer.parseInt(sectionTypeId);
        return new SectionType(id);
    }
    
}
