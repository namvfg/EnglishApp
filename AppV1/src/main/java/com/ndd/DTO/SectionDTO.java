/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.DTO;


/**
 *
 * @author Admin
 */
public class SectionDTO {
    private Integer id;
    private String content;
    private String question;
    private String answer;
    private String correctAnswer;
    private String sectionTypeName;
    private String saveType;

    public SectionDTO() {
    }

    public SectionDTO(Integer id, String content, String question, String answer, String correctAnswer, String sectionTypeName, String saveType) {
        this.id = id;
        this.content = content;
        this.question = question;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
        this.sectionTypeName = sectionTypeName;
        this.saveType = saveType;
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
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
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
     * @return the correctAnswer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * @param correctAnswer the correctAnswer to set
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
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
     * @return the saveType
     */
    public String getSaveType() {
        return saveType;
    }

    /**
     * @param saveType the saveType to set
     */
    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }
}
