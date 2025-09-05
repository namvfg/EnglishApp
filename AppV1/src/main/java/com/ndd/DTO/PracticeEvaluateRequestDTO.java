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
public class PracticeEvaluateRequestDTO {
    private Integer userId;
    private PracticeSessionDTO practiceSession;
    private List<UserAnswerDTO> userAnswers;
    private Float score;

    public PracticeEvaluateRequestDTO() {
    }

    public PracticeEvaluateRequestDTO(Integer userId, PracticeSessionDTO practiceSession, List<UserAnswerDTO> userAnswers, Float score) {
        this.userId = userId;
        this.practiceSession = practiceSession;
        this.userAnswers = userAnswers;
        this.score = score;
    }

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return the practiceSession
     */
    public PracticeSessionDTO getPracticeSession() {
        return practiceSession;
    }

    /**
     * @param practiceSession the practiceSession to set
     */
    public void setPracticeSession(PracticeSessionDTO practiceSession) {
        this.practiceSession = practiceSession;
    }

    /**
     * @return the userAnswers
     */
    public List<UserAnswerDTO> getUserAnswers() {
        return userAnswers;
    }

    /**
     * @param userAnswers the userAnswers to set
     */
    public void setUserAnswers(List<UserAnswerDTO> userAnswers) {
        this.userAnswers = userAnswers;
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
