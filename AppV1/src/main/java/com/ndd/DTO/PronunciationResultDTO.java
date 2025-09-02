/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.DTO;

/**
 *
 * @author Admin
 */
public class PronunciationResultDTO {

    private String transcript;
    private float pronunciationScore;
    private float fluencyScore;
    private float completenessScore;
    private float accuracyScore;

    public PronunciationResultDTO() {
    }

    public PronunciationResultDTO(String transcript, float pronunciationScore, float fluencyScore, float completenessScore, float accuracyScore) {
        this.transcript = transcript;
        this.pronunciationScore = pronunciationScore;
        this.fluencyScore = fluencyScore;
        this.completenessScore = completenessScore;
        this.accuracyScore = accuracyScore;
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
     * @return the pronunciationScore
     */
    public float getPronunciationScore() {
        return pronunciationScore;
    }

    /**
     * @param pronunciationScore the pronunciationScore to set
     */
    public void setPronunciationScore(float pronunciationScore) {
        this.pronunciationScore = pronunciationScore;
    }

    /**
     * @return the fluencyScore
     */
    public float getFluencyScore() {
        return fluencyScore;
    }

    /**
     * @param fluencyScore the fluencyScore to set
     */
    public void setFluencyScore(float fluencyScore) {
        this.fluencyScore = fluencyScore;
    }

    /**
     * @return the completenessScore
     */
    public float getCompletenessScore() {
        return completenessScore;
    }

    /**
     * @param completenessScore the completenessScore to set
     */
    public void setCompletenessScore(float completenessScore) {
        this.completenessScore = completenessScore;
    }

    /**
     * @return the accuracyScore
     */
    public float getAccuracyScore() {
        return accuracyScore;
    }

    /**
     * @param accuracyScore the accuracyScore to set
     */
    public void setAccuracyScore(float accuracyScore) {
        this.accuracyScore = accuracyScore;
    }
}
