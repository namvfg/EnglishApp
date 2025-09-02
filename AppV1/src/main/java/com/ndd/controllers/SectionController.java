package com.ndd.controllers;

import com.ndd.pojo.Section;
import com.ndd.pojo.Lesson;
import com.ndd.service.SectionService;
import com.ndd.service.LessonService;
import com.ndd.service.SectionTypeService;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private SectionTypeService sectionTypeService;

    @GetMapping("/categories/{cateId}/lessons/{lessonId}/sections")
    public String list(
            @PathVariable("cateId") int cateId,
            @PathVariable("lessonId") int lessonId,
            Model model) {

        model.addAttribute("categoryId", cateId);
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("section", new Section());
        model.addAttribute("sectionTypes", this.sectionTypeService.getSectionTypes());

        return "sections"; // Tên view
    }

    @GetMapping("/categories/{cateId}/lessons/{lessonId}/sections/{id}")
    public String update(
            @PathVariable("cateId") int cateId,
            @PathVariable("lessonId") int lessonId,
            @PathVariable("id") int id,
            Model model) {

        model.addAttribute("categoryId", cateId);
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("section", this.sectionService.getSectionById(id));
        model.addAttribute("sectionTypes", this.sectionTypeService.getSectionTypes());

        return "sections"; // Vẫn dùng cùng view, chỉ thay đổi dữ liệu form
    }

    @PostMapping("/categories/{cateId}/lessons/{lessonId}/sections")
    public String addOrUpdate(
            @ModelAttribute("section") @Valid Section s,
            BindingResult rs,
            @PathVariable("cateId") int cateId,
            @PathVariable("lessonId") int lessonId,
            RedirectAttributes redirectAttributes) {

        if (!rs.hasErrors()) {
            Date now = new Date();
            if (s.getId() == null) {
                s.setCreatedDate(now);
                s.setUpdatedDate(now);
            } else {
                Section old = this.sectionService.getSectionById(s.getId());
                s.setCreatedDate(old.getCreatedDate());
                s.setUpdatedDate(now);
            }

            Lesson lesson = this.lessonService.getLessonById(lessonId);
            s.setLessonId(lesson);

            if (this.sectionService.addOrUpdateSection(s)) {
                redirectAttributes.addFlashAttribute("toastMessage", "Thêm/sửa section thành công");
                redirectAttributes.addFlashAttribute("toastType", "success");
            } else {
                redirectAttributes.addFlashAttribute("toastMessage", "Thêm/sửa section thất bại");
                redirectAttributes.addFlashAttribute("toastType", "error");
            }

            return String.format("redirect:/categories/%d/lessons/%d", cateId, lessonId);
        }

        redirectAttributes.addFlashAttribute("toastMessage", "Dữ liệu không hợp lệ");
        redirectAttributes.addFlashAttribute("toastType", "error");
        return String.format("redirect:/categories/%d/lessons/%d", cateId, lessonId);
    }

    @GetMapping("/categories/{cateId}/lessons/{lessonId}/sections/{id}/delete")
    public String delete(
            @PathVariable("cateId") int cateId,
            @PathVariable("lessonId") int lessonId,
            @PathVariable("id") int id,
            RedirectAttributes redirectAttributes) {

        Section s = this.sectionService.getSectionById(id);
        if (s != null) {
            s.setLessonId(null); // nếu có ràng buộc, tránh lỗi constraint
        }

        boolean deleted = false;
        try {
            deleted = this.sectionService.deleteSectionById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (deleted) {
            redirectAttributes.addFlashAttribute("toastMessage", "Xóa section thành công");
            redirectAttributes.addFlashAttribute("toastType", "success");
        } else {
            redirectAttributes.addFlashAttribute("toastMessage", "Xóa thất bại");
            redirectAttributes.addFlashAttribute("toastType", "error");
        }

        return String.format("redirect:/categories/%d/lessons/%d/sections", cateId, lessonId);
    }
}
