/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service;

/**
 *
 * @author Admin
 */
public interface OtpService {

    public void saveOtp(String email, String otp);

    public boolean verifyOtp(String email, String otpInput);

    public void invalidateOtp(String email);

    public String generateRandomOtp();
}
