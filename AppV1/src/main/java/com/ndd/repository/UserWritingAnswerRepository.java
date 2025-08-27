/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.repository;

import com.ndd.pojo.UserWritingAnswer;

/**
 *
 * @author Admin
 */
public interface UserWritingAnswerRepository {
    boolean addUserWritingAnswer(UserWritingAnswer uwa);
    
    UserWritingAnswer getUWAById(int id);
}
