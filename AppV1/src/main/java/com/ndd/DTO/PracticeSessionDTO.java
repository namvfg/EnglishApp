/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.DTO;

/**
 *
 * @author Admin
 */
public class PracticeSessionDTO {

    private Integer lessonId;
    private Integer duration;
    private String startAt;

    public PracticeSessionDTO() {
    }

    public PracticeSessionDTO(Integer lessonId, Integer duration, String startAt) {
        this.lessonId = lessonId;
        this.duration = duration;
        this.startAt = startAt;
    }
    
    /**
     * @return the lessonId
     */
    public Integer getLessonId() {
        return lessonId;
    }

    /**
     * @param lessonId the lessonId to set
     */
    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    /**
     * @return the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * @return the startAt
     */
    public String getStartAt() {
        return startAt;
    }

    /**
     * @param startAt the startAt to set
     */
    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }
}
