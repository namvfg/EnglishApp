/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Admin
 */
public class SectionDTO {

    private int id;

    @NotNull
    private Integer sectionTypeId;

    private String sectionTypeName;
    
    private Integer lessonId;

    @NotBlank
    private String content;

    public SectionDTO() {
    }

    public SectionDTO(int id, int sectionTypeId, String sectionTypeName, int lessonId, String content) {
        this.id = id;
        this.sectionTypeId = sectionTypeId;
        this.sectionTypeName = sectionTypeName;
        this.lessonId = lessonId;
        this.content = content;
    }

   
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the sectionTypeId
     */
    public int getSectionTypeId() {
        return sectionTypeId;
    }

    /**
     * @param sectionTypeId the sectionTypeId to set
     */
    public void setSectionTypeId(int sectionTypeId) {
        this.sectionTypeId = sectionTypeId;
    }

    /**
     * @return the sectionTypeName
     */
    public String getSectionTypeName() {
        return sectionTypeName;
    }

    /**
     * @param sectionTypeName the sectionTypeName to set
     */
    public void setSectionTypeName(String sectionTypeName) {
        this.sectionTypeName = sectionTypeName;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the lessonId
     */
    public int getLessonId() {
        return lessonId;
    }

    /**
     * @param lessonId the lessonId to set
     */
    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

}
