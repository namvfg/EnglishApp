/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.service.EmailService;
import com.ndd.service.OtpService;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@PropertySource("classpath:application.properties")
public class OtpServiceImpl implements OtpService {

    @Value("${otp.expire-seconds}")
    private long expireSeconds;

    @Value("${otp.max-attempts}")
    private int maxAttempts;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private EmailService emailService;

    private String otpKey(String email) {
        return "OTP:" + email;
    }

    private String attemptKey(String email) {
        return "ATTEMPT:" + email;
    }

    @Override
    public void saveOtp(String email, String otp) {
        redisTemplate.opsForValue().set(otpKey(email), otp, expireSeconds, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(attemptKey(email), "0", expireSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean verifyOtp(String email, String inputOtp) {
        String otp = redisTemplate.opsForValue().get(otpKey(email));

        if (otp == null) {
            return false;
        }

        if (otp.equals(inputOtp)) {
            invalidateOtp(email);
            redisTemplate.opsForValue().set("otp:verified:" + email, "true", Duration.ofMinutes(10));
            return true;
        } else {
            String attemptStr = redisTemplate.opsForValue().get(attemptKey(email));
            int attempts = attemptStr != null ? Integer.parseInt(attemptStr) : 0;
            attempts++;

            if (attempts >= maxAttempts) {
                invalidateOtp(email); // xóa OTP cũ
                
                String newOtp = generateRandomOtp();
                saveOtp(email, newOtp); // lưu lại OTP mới
                emailService.sendOtp(email, newOtp); // gửi email
                throw new RuntimeException("Bạn đã nhập sai quá " + maxAttempts + " lần. Mã OTP mới đã được gửi.");
            }

            redisTemplate.opsForValue().set(attemptKey(email), String.valueOf(attempts), Duration.ofSeconds(expireSeconds));
            return false;
        }
    }

    @Override
    public String generateRandomOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    @Override
    public void invalidateOtp(String email) {
        redisTemplate.delete(otpKey(email));
        redisTemplate.delete(attemptKey(email));
    }
}
