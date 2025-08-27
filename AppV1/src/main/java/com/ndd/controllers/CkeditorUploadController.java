/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.service.CloudinaryService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/ckeditor")
public class CkeditorUploadController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImageCKE5(@RequestParam("upload") MultipartFile file) {
        try {
            String url = cloudinaryService.uploadImage(file, "ckeditor").get("secure_url").toString();
            return ResponseEntity.ok(Map.of(
                    "uploaded", 1, 
                    "fileName", file.getOriginalFilename(), 
                    "url", url 
            ));
        } catch (Exception ex) {
            return ResponseEntity.ok(Map.of(
                    "uploaded", 0,
                    "error", Map.of("message", "Upload failed: " + ex.getMessage())
            ));
        }
    }

}
