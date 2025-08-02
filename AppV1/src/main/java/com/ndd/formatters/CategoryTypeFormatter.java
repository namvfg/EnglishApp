/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.formatters;

import com.ndd.pojo.CategoryType;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Admin
 */
public class CategoryTypeFormatter implements Formatter<CategoryType>{

    @Override
    public String print(CategoryType categoryType, Locale locale) {
        return String.valueOf(categoryType.getId());
    }

    @Override
    public CategoryType parse(String catetegoryTypeId, Locale locale) throws ParseException {
        int id = Integer.parseInt(catetegoryTypeId);
        return new CategoryType(id);
    }
}
