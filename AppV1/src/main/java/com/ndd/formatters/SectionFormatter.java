/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.formatters;

import com.ndd.pojo.Section;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Admin
 */
public class SectionFormatter implements Formatter<Section> {

    @Override
    public String print(Section section, Locale locale) {
        return String.valueOf(section.getId());
    }

    @Override
    public Section parse(String sectionId, Locale locale) throws ParseException {
        int id = Integer.parseInt(sectionId);
        return new Section(id);
    }
}
