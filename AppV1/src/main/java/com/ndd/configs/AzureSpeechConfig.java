/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.configs;

import com.microsoft.cognitiveservices.speech.SpeechConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Admin
 */
@Configurable
@PropertySource("classpath:configs.properties")
public class AzureSpeechConfig {

    @Value("${azure.speech.key}")
    private String azureSpeechKey;

    @Value("${azure.speech.region}")
    private String azureSpeechRegion;

    @Bean
    public SpeechConfig speechConfig() {
        SpeechConfig config = SpeechConfig.fromSubscription(azureSpeechKey, azureSpeechRegion);
        config.setSpeechRecognitionLanguage("en-US");
        return config;
    }
}
