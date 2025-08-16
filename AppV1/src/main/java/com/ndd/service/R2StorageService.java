/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ndd.service;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
public interface R2StorageService {
    String uploadFile(MultipartFile file);
    
    void deleteByUrl(String url);
}
