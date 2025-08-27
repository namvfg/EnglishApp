/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.DTO;

import java.util.List;

/**
 *
 * @author Admin
 */
public class SpeakingLessonDTO {
    
    private int id;
    private String title;
    private String image;
    private List<String> questions;
    private String introduction;

    public SpeakingLessonDTO() {
    }

    public SpeakingLessonDTO(int id, String title, String image, List<String> questions, String introduction) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.questions = questions;
        this.introduction = introduction;
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
     * @return the questions
     */
    public List<String> getQuestions() {
        return questions;
    }

    /**
     * @param questions the questions to set
     */
    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    /**
     * @return the introduction
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * @param introduction the introduction to set
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
