/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service;

import com.ndd.DTO.PronunciationResultDTO;

/**
 *
 * @author Admin
 */
public interface AzureSpeechService {
    PronunciationResultDTO analyzePronunciation(String audioFilePath, String referenceText) throws Exception;
    
    public String transcribeOnly(String audioFilePath) throws Exception;
}
