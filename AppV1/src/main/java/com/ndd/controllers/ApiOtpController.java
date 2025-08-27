/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.ndd.service.EmailService;
import com.ndd.service.OtpService;
import com.ndd.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class ApiOtpController {

    @Autowired
    private OtpService otpService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;

    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtp(@RequestParam String email) {

        // Email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email không hợp lệ"));
        }

        // Kiểm tra email đã tồn tại chưa
        if (userService.getUserByEmail(email) != null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email đã được sử dụng"));
        }

        // Sinh OTP
        String otp = otpService.generateRandomOtp();
        otpService.saveOtp(email, otp);

        // Gửi email OTP
        emailService.sendOtp(email, otp);

        System.out.println("OTP cho " + email + " là: " + otp);
        return ResponseEntity.ok(Map.of("message", "OTP đã được gửi"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        try {
            boolean valid = otpService.verifyOtp(email, otp);
            if (valid) {
                return ResponseEntity.ok(Map.of("message", "Xác thực thành công"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "OTP không chính xác hoặc đã hết hạn"));
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", ex.getMessage()));
        }

    }
}
