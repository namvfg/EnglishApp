/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ndd.pojo.Lesson;
import com.ndd.repository.LessonRepository;
import com.ndd.service.LessonService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
@Transactional
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepo;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Lesson> getLessonsByCategoryId(int categoryId, Map<String, String> params) {
        return this.lessonRepo.getLessonsByCategoryId(categoryId, params);
    }

    @Override
    public long countLessonsByCategoryId(int categoryId, Map<String, String> params) {
        return this.lessonRepo.countLessonsByCategoryId(categoryId, params);
    }

    @Override
    public boolean addOrUpdateLesson(Lesson l) {
        try {
            // Kiểm tra ảnh cũ
            Lesson old = (l.getId() != null) ? lessonRepo.getLessonById(l.getId()) : null;
            String oldImageUrl = (old != null) ? old.getImage() : null;
            String oldPublicId = extractPublicIdFromUrl(oldImageUrl);

            // Nếu user upload ảnh mới
            if (l.getFile() != null && !l.getFile().isEmpty()) {
                // Upload ảnh mới
                Map uploadRes = cloudinary.uploader().upload(
                        l.getFile().getBytes(),
                        ObjectUtils.asMap(
                                "resource_type", "image",
                                "folder", "lessons", // gợi ý: gom theo thư mục
                                "use_filename", true,
                                "unique_filename", true,
                                "overwrite", true
                        )
                );

                String newUrl = uploadRes.get("secure_url").toString();
                String newPublicId = uploadRes.get("public_id").toString();

                // Gán URL mới
                l.setImage(newUrl);

                // Xóa ảnh cũ sau khi upload mới OK (nếu có và khác ảnh mới)
                if (oldPublicId != null && !oldPublicId.equals(newPublicId)) {
                    try {
                        cloudinary.uploader().destroy(
                                oldPublicId,
                                ObjectUtils.asMap("invalidate", true, "resource_type", "image")
                        );
                    } catch (Exception delEx) {
                        // Không fail giao dịch vì lỗi xóa ảnh cũ; chỉ log nếu cần
                        Logger.getLogger(getClass().getName()).log(Level.WARNING,
                                "Delete old image failed: " + delEx.getMessage(), delEx);
                    }
                }

            } else {
                // Không chọn file mới:
                if (old != null) {
                    l.setImage(old.getImage());
                } 
            }
            return lessonRepo.addOrUpdateLesson(l);

        } catch (IOException ex) {
            Logger.getLogger(LessonServiceImpl.class.getName()).log(Level.SEVERE, "Cloudinary upload failed", ex);
            return false;
        }
    }

    private String extractPublicIdFromUrl(String url) {
        if (url == null || url.isBlank()) {
            return null;
        }
        try {
            String path = new java.net.URI(url).getPath(); // /<cloud>/image/upload/v12345/lessons/abc.png
            int uploadIdx = path.indexOf("/upload/");
            if (uploadIdx == -1) {
                return null;
            }
            String after = path.substring(uploadIdx + "/upload/".length()); // v12345/lessons/abc.png
            // bỏ "v<digits>/" nếu có
            after = after.replaceFirst("^v\\d+/", "");
            // bỏ phần mở rộng
            int dot = after.lastIndexOf('.');
            return (dot > 0) ? after.substring(0, dot) : after;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Lesson getLessonById(int id) {
        return this.lessonRepo.getLessonById(id);
    }

    @Override
    public List<Lesson> getLessons(Map<String, String> params) {
        return this.lessonRepo.getLessons(params);
    }

    @Override
    public long countLessons(Map<String, String> params) {
        return this.lessonRepo.countLessons(params);
    }
}
