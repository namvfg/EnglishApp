/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.enums.Skill;
import com.ndd.pojo.Category;
import com.ndd.pojo.Lesson;
import com.ndd.service.CategoryService;
import com.ndd.service.LessonService;
import com.ndd.service.R2StorageService;
import com.ndd.service.SectionService;
import com.ndd.service.SectionTypeService;
import java.io.IOException;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Admin
 */
@Controller
public class LessonController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private SectionTypeService sectionTypeService;
    @Autowired
    private R2StorageService r2StorageService;

    @ModelAttribute
    public void commonAttr(Model model) {
        model.addAttribute("skills", Skill.values());
    }

    @GetMapping("/categories/{cateId}/lessons")
    public String list(Model model, @PathVariable(value = "cateId") int cateId) {
        model.addAttribute("lesson", new Lesson());
        model.addAttribute("selectedSkill", Skill.listening);
        Category category = categoryService.getCategoryById(cateId);
        if (category == null) {
            // Redirect về trang lỗi hoặc thông báo
            return "redirect:/error?msg=category_not_found";
        }
        model.addAttribute("category", category);
        return "lessons";
    }

    @GetMapping("/categories/{cateId}/lessons/{id}")
    public String update(Model model, @PathVariable(value = "id") int id, @PathVariable(value = "cateId") int cateId) {
        Lesson lesson = this.lessonService.getLessonById(id);
        model.addAttribute("lesson", lesson);
        model.addAttribute("selectedSkill", lesson.getLessonTypeId().getSkill());
        model.addAttribute("category", categoryService.getCategoryById(cateId));
        model.addAttribute("sections", sectionService.getSectionsByLessonId(lesson.getId()));
        model.addAttribute("sectionTypes", sectionTypeService.getSectionTypes());
        return "lessons";
    }

    @PostMapping("/categories/{cateId}/lessons")
    public String add(
            @PathVariable int cateId,
            @ModelAttribute("lesson") @Valid Lesson l,
            BindingResult rs,
            @RequestParam(value = "audio-file", required = false) MultipartFile audioFile,
            @RequestParam(value = "skill") String skill,
            Model model,
            RedirectAttributes redirectAttributes
    ) throws IOException {

        if (Skill.valueOf(skill) == Skill.listening) {
            String oldUrl = null;
            if (l.getId() != null) {
                Lesson old = lessonService.getLessonById(l.getId());
                oldUrl = old != null ? old.getContent() : null;
            }

            if (audioFile == null || audioFile.isEmpty()) {
                if (l.getId() == null) {
                    rs.rejectValue("content", "required", "Hãy chọn file audio cho Listening");
                    return "lessons";
                } else {
                    // edit: giữ URL cũ
                    l.setContent(oldUrl);
                }
            } else {
                try {
                    String url = r2StorageService.uploadFile(audioFile);
                    if (url == null || url.isEmpty()) {
                        rs.rejectValue("content", "invalid", "Upload thất bại, URL trống");
                        return "lessons";
                    }
                    l.setContent(url);

                    // XÓA FILE CŨ (sau khi upload mới thành công)
                    if (oldUrl != null && !oldUrl.isBlank() && !oldUrl.equals(url)) {
                        try {
                            r2StorageService.deleteByUrl(oldUrl);
                        } catch (Exception delEx) {
                            System.out.println("Delete old audio failed: " + delEx.getMessage());
                        }
                    }
                } catch (Exception ex) {
                    rs.rejectValue("content", "invalid", "Upload thất bại: " + ex.getMessage());
                    return "lessons";
                }
            }
        }
        if (rs.hasErrors()) {
            redirectAttributes.addFlashAttribute("toastType", "errror");
            redirectAttributes.addFlashAttribute("toastMessage", "Có lỗi xảy ra, vui lòng thử lại");
            return "lessons";
        }

        // 4) Metadata & lưu
        Category category = categoryService.getCategoryById(cateId);
        Date now = new Date();

        if (l.getId() == null) {
            l.setCreatedDate(now);
            l.setUpdatedDate(now);
            l.setCategoryId(category);
        } else {
            Lesson old = lessonService.getLessonById(l.getId());
            l.setCreatedDate(old.getCreatedDate());
            l.setUpdatedDate(now);
            l.setCategoryId(category);
        }

        if (lessonService.addOrUpdateLesson(l)) {
            redirectAttributes.addFlashAttribute("toastType", "success");
            redirectAttributes.addFlashAttribute("toastMessage", "Thêm lesson thành công");
        }
        redirectAttributes.addAttribute("category", category);
        return "redirect:/categories/" + cateId;
    }

    @GetMapping("/categories/{cateId}/lessons/{id}/delete")
    public String deleteCategory(
            @PathVariable("id") int id,
            @PathVariable("cateId") int cateId,
            RedirectAttributes redirectAttributes) {
        boolean deleted = lessonService.deleteLessonById(id);

        if (deleted) {
            redirectAttributes.addFlashAttribute("toastMessage", "Xóa lesson thành công");
            redirectAttributes.addFlashAttribute("toastType", "success");
        } else {
            redirectAttributes.addFlashAttribute("toastMessage", "Không tìm thấy lesson");
            redirectAttributes.addFlashAttribute("toastType", "error");
        }
        
        return "redirect:/categories/" + cateId;
    }
}
