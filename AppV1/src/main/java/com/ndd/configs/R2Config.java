/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.configs;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

/**
 *
 * @author Admin
 */
@Configuration
@PropertySource("classpath:configs.properties")
public class R2Config {

    @Value("${cloudflare.access_key}")
    private String accessKey;

    @Value("${cloudflare.secret_key}")
    private String secretKey;

    @Value("${cloudflare.endpoint_url}")
    private String endpoint;

    @Value("${cloudflare.bucket_name}")
    private String bucketName;
    

    @Bean
    public S3Client s3Client() {
        S3Configuration serviceConfig = S3Configuration.builder()
                // path-style is required for R2
                .pathStyleAccessEnabled(true)
                // disable AWS4 chunked uploads
                .chunkedEncodingEnabled(false)
                .build();

        return S3Client.builder()
                .httpClientBuilder(ApacheHttpClient.builder())
                .region(Region.of("auto"))
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                accessKey,
                                secretKey)))
                .serviceConfiguration(serviceConfig)
                .build();
    }

    @Bean
    public String r2BucketName() {
        return bucketName;
    }
   
}
