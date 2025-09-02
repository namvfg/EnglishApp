/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.microsoft.cognitiveservices.speech.PronunciationAssessmentConfig;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentResult;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.ndd.DTO.PronunciationResultDTO;
import com.ndd.service.AzureSpeechService;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class AzureSpeechServiceImpl implements AzureSpeechService {

    @Autowired
    private SpeechConfig speechConfig;

    @Override
    public PronunciationResultDTO analyzePronunciation(String audioFilePath, String referenceText) throws Exception {
        AudioConfig audioConfig = AudioConfig.fromWavFileInput(audioFilePath);

        String configJson = "{"
                + "\"referenceText\": \"" + referenceText + "\","
                + "\"gradingSystem\": \"HundredPoint\","
                + "\"granularity\": \"Phoneme\","
                + "\"enableMiscue\": true"
                + "}";

        PronunciationAssessmentConfig config = PronunciationAssessmentConfig.fromJson(configJson);

        try (SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig)) {
            config.applyTo(recognizer);

            Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
            SpeechRecognitionResult result = task.get();

            if (result.getReason() == ResultReason.RecognizedSpeech) {
                PronunciationAssessmentResult assessment = PronunciationAssessmentResult.fromResult(result);

                return new PronunciationResultDTO(
                        result.getText(),
                        parseSafe(assessment.getPronunciationScore()),
                        parseSafe(assessment.getFluencyScore()),
                        parseSafe(assessment.getCompletenessScore()),
                        parseSafe(assessment.getAccuracyScore())
                );
            } else {
                throw new RuntimeException("Speech not recognized or failed: " + result.getReason());
            }
        }
    }

    private float parseSafe(Object obj) {
        if (obj == null) {
            return 0f;
        }
        try {
            return Float.parseFloat(obj.toString());
        } catch (Exception e) {
            return 0f;
        }
    }

    @Override
    public String transcribeOnly(String audioFilePath) throws Exception {
        AudioConfig audioConfig = AudioConfig.fromWavFileInput(audioFilePath);

        try (SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig)) {
            Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
            SpeechRecognitionResult result = task.get();

            if (result.getReason() == ResultReason.RecognizedSpeech) {
                return result.getText();
            } else {
                throw new RuntimeException("Failed to transcribe audio: " + result.getReason());
            }
        }
    }

}
