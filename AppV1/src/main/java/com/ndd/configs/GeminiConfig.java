/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.configs;

import com.ndd.ai.GeminiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Admin
 */
@Configuration
@PropertySource("classpath:configs.properties")
public class GeminiConfig {

    @Value("${gemini.api.key}")
    private String geminiApiKey;
    
    @Bean
    public GeminiClient geminiClient() {
        return new GeminiClient(geminiApiKey);
    }
}
