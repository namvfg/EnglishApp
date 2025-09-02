/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service;

import com.ndd.DTO.GeminiSpeakingScoreDTO;
import com.ndd.DTO.GeminiWritingScoreDTO;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface GeminiService {

    String evaluateWriting(String lessonTypeName, String promptContentHtml, String essayText) throws Exception;

    GeminiWritingScoreDTO parseGeminiWritingResponse(String json) throws IOException;
    
    String evaluateSpeaking(String title, List<String> questions, String transcript) throws Exception;
    
    GeminiSpeakingScoreDTO parseGeminiSpeakingResponse(String json) throws IOException;
    
}
