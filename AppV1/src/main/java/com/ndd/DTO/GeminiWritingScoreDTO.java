package com.ndd.DTO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class GeminiWritingScoreDTO {
    private String content;
    private String feedback;
    private float task_score;
    private float coherence_score;
    private float lexical_score;
    private float grammar_score;
    private float overall_score;

    public GeminiWritingScoreDTO() {
    }

    public GeminiWritingScoreDTO(String content, String feedback, float task_score, float coherence_score, float lexical_score, float grammar_score, float overall_score) {
        this.content = content;
        this.feedback = feedback;
        this.task_score = task_score;
        this.coherence_score = coherence_score;
        this.lexical_score = lexical_score;
        this.grammar_score = grammar_score;
        this.overall_score = overall_score;
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
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * @return the task_score
     */
    public float getTask_score() {
        return task_score;
    }

    /**
     * @param task_score the task_score to set
     */
    public void setTask_score(float task_score) {
        this.task_score = task_score;
    }

    /**
     * @return the coherence_score
     */
    public float getCoherence_score() {
        return coherence_score;
    }

    /**
     * @param coherence_score the coherence_score to set
     */
    public void setCoherence_score(float coherence_score) {
        this.coherence_score = coherence_score;
    }

    /**
     * @return the lexical_score
     */
    public float getLexical_score() {
        return lexical_score;
    }

    /**
     * @param lexical_score the lexical_score to set
     */
    public void setLexical_score(float lexical_score) {
        this.lexical_score = lexical_score;
    }

    /**
     * @return the grammar_score
     */
    public float getGrammar_score() {
        return grammar_score;
    }

    /**
     * @param grammar_score the grammar_score to set
     */
    public void setGrammar_score(float grammar_score) {
        this.grammar_score = grammar_score;
    }

    /**
     * @return the overall_score
     */
    public float getOverall_score() {
        return overall_score;
    }

    /**
     * @param overall_score the overall_score to set
     */
    public void setOverall_score(float overall_score) {
        this.overall_score = overall_score;
    }
    
    
}
