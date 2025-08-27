/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service;

import com.ndd.DTO.GeminiWritingScoreDTO;
import java.io.IOException;

/**
 *
 * @author Admin
 */
public interface GeminiService {

    String evaluate(String lessonTypeName, String promptContentHtml, String essayText) throws Exception;

    GeminiWritingScoreDTO parseGeminiResponse(String json) throws IOException;
    
}
