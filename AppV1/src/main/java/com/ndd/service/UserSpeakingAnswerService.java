/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service;

import com.ndd.pojo.UserSpeakingAnswer;

/**
 *
 * @author Admin
 */
public interface UserSpeakingAnswerService {

    boolean addUserSpeakingAnswer(UserSpeakingAnswer usa);

    UserSpeakingAnswer getUSAById(int id);
}
