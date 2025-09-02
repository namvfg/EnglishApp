package com.ndd.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    
    @JsonProperty("task_score")
    private float taskScore;

    @JsonProperty("coherence_score")
    private float coherenceScore;

    @JsonProperty("lexical_score")
    private float lexicalScore;

    @JsonProperty("grammar_score")
    private float grammarScore;

    @JsonProperty("overall_score")
    private float overallScore;

    public GeminiWritingScoreDTO() {
    }

    public GeminiWritingScoreDTO(String content, String feedback, float task_score, float coherence_score, float lexical_score, float grammar_score, float overall_score) {
        this.content = content;
        this.feedback = feedback;
        this.taskScore = task_score;
        this.coherenceScore = coherence_score;
        this.lexicalScore = lexical_score;
        this.grammarScore = grammar_score;
        this.overallScore = overall_score;
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
     * @return the taskScore
     */
    public float getTaskScore() {
        return taskScore;
    }

    /**
     * @param taskScore the taskScore to set
     */
    public void setTaskScore(float taskScore) {
        this.taskScore = taskScore;
    }

    /**
     * @return the coherenceScore
     */
    public float getCoherenceScore() {
        return coherenceScore;
    }

    /**
     * @param coherenceScore the coherenceScore to set
     */
    public void setCoherenceScore(float coherenceScore) {
        this.coherenceScore = coherenceScore;
    }

    /**
     * @return the lexicalScore
     */
    public float getLexicalScore() {
        return lexicalScore;
    }

    /**
     * @param lexicalScore the lexicalScore to set
     */
    public void setLexicalScore(float lexicalScore) {
        this.lexicalScore = lexicalScore;
    }

    /**
     * @return the grammarScore
     */
    public float getGrammarScore() {
        return grammarScore;
    }

    /**
     * @param grammarScore the grammarScore to set
     */
    public void setGrammarScore(float grammarScore) {
        this.grammarScore = grammarScore;
    }

    /**
     * @return the overallScore
     */
    public float getOverallScore() {
        return overallScore;
    }

    /**
     * @param overallScore the overallScore to set
     */
    public void setOverallScore(float overallScore) {
        this.overallScore = overallScore;
    }

}
