/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.DTO;

/**
 *
 * @author Admin
 */
public class UserAnswerDTO {

    private Integer sectionId;
    private String answer;
    private Boolean isCorrect;
    private Float score;

    public UserAnswerDTO() {
    }

    public UserAnswerDTO(Integer sectionId, String answer, Boolean isCorrect, Float score) {
        this.sectionId = sectionId;
        this.answer = answer;
        this.isCorrect = isCorrect;
        this.score = score;
    }

    /**
     * @return the sectionId
     */
    public Integer getSectionId() {
        return sectionId;
    }

    /**
     * @param sectionId the sectionId to set
     */
    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return the isCorrect
     */
    public Boolean getIsCorrect() {
        return isCorrect;
    }

    /**
     * @param isCorrect the isCorrect to set
     */
    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    /**
     * @return the score
     */
    public Float getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(Float score) {
        this.score = score;
    }
}
