/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Admin
 */
public class GeminiSpeakingScoreDTO {

    private String transcript;
    private String feedback;
    @JsonProperty("pronunciation_score")
    private Float pronunciationScore;

    @JsonProperty("fluency_score")
    private Float fluencyScore;

    @JsonProperty("coherence_score")
    private Float coherenceScore;

    @JsonProperty("lexical_resource_score")
    private Float lexicalResourceScore;

    @JsonProperty("grammar_score")
    private Float grammarScore;

    @JsonProperty("overall_score")
    private Float overallScore;

    public GeminiSpeakingScoreDTO() {
    }

    public GeminiSpeakingScoreDTO(String transcript, String feedback, Float pronunciation_score, Float fluency_score, Float coherence_score, Float lexical_resource_score, Float grammar_score, Float overall_score) {
        this.transcript = transcript;
        this.feedback = feedback;
        this.pronunciationScore = pronunciation_score;
        this.fluencyScore = fluency_score;
        this.coherenceScore = coherence_score;
        this.lexicalResourceScore = lexical_resource_score;
        this.grammarScore = grammar_score;
        this.overallScore = overall_score;
    }

    /**
     * @return the transcript
     */
    public String getTranscript() {
        return transcript;
    }

    /**
     * @param transcript the transcript to set
     */
    public void setTranscript(String transcript) {
        this.transcript = transcript;
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
     * @return the fluencyScore
     */
    public Float getFluencyScore() {
        return fluencyScore;
    }

    /**
     * @param fluencyScore the fluencyScore to set
     */
    public void setFluencyScore(Float fluencyScore) {
        this.fluencyScore = fluencyScore;
    }

    /**
     * @return the coherenceScore
     */
    public Float getCoherenceScore() {
        return coherenceScore;
    }

    /**
     * @param coherenceScore the coherenceScore to set
     */
    public void setCoherenceScore(Float coherenceScore) {
        this.coherenceScore = coherenceScore;
    }

    /**
     * @return the lexicalResourceScore
     */
    public Float getLexicalResourceScore() {
        return lexicalResourceScore;
    }

    /**
     * @param lexicalResourceScore the lexicalResourceScore to set
     */
    public void setLexicalResourceScore(Float lexicalResourceScore) {
        this.lexicalResourceScore = lexicalResourceScore;
    }

    /**
     * @return the grammarScore
     */
    public Float getGrammarScore() {
        return grammarScore;
    }

    /**
     * @param grammarScore the grammarScore to set
     */
    public void setGrammarScore(Float grammarScore) {
        this.grammarScore = grammarScore;
    }

    /**
     * @return the overallScore
     */
    public Float getOverallScore() {
        return overallScore;
    }

    /**
     * @param overallScore the overallScore to set
     */
    public void setOverallScore(Float overallScore) {
        this.overallScore = overallScore;
    }

    /**
     * @return the pronunciationScore
     */
    public Float getPronunciationScore() {
        return pronunciationScore;
    }

    /**
     * @param pronunciationScore the pronunciationScore to set
     */
    public void setPronunciationScore(Float pronunciationScore) {
        this.pronunciationScore = pronunciationScore;
    }
}
