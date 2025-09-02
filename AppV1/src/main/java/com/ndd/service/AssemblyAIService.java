/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
public interface AssemblyAIService {
    String transcribe(MultipartFile audioFile) throws Exception;
}
