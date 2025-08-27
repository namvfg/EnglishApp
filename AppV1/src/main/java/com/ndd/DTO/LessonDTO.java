/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.DTO;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class LessonDTO {
    private Integer id;
    private String title;
    private String image;
    private String content;
    private Date createdDate;
    private Date updatedDate;
    private Integer categoryId;
    private String categoryName;
    private Integer lessonTypeId;
    private String lessonTypeName;
    private String skill;

    public LessonDTO() {
    }

    public LessonDTO(Integer id, String title, String image, String content, Date createdDate, Date updatedDate, Integer categoryId, String categoryName, Integer lessonTypeId, String lessonTypeName, String skill) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.lessonTypeId = lessonTypeId;
        this.lessonTypeName = lessonTypeName;
        this.skill = skill;
    }
    
   
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
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
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the categoryId
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the lessonTypeId
     */
    public Integer getLessonTypeId() {
        return lessonTypeId;
    }

    /**
     * @param lessonTypeId the lessonTypeId to set
     */
    public void setLessonTypeId(Integer lessonTypeId) {
        this.lessonTypeId = lessonTypeId;
    }

    /**
     * @return the lessonTypeName
     */
    public String getLessonTypeName() {
        return lessonTypeName;
    }

    /**
     * @param lessonTypeName the lessonTypeName to set
     */
    public void setLessonTypeName(String lessonTypeName) {
        this.lessonTypeName = lessonTypeName;
    }

    /**
     * @return the skill
     */
    public String getSkill() {
        return skill;
    }

    /**
     * @param skill the skill to set
     */
    public void setSkill(String skill) {
        this.skill = skill;
    }
    
}
