/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service;

import com.ndd.pojo.UserAnswer;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface UserAnswerService {

    boolean add(UserAnswer userAnswer);

    boolean addAll(List<UserAnswer> answers);

    List<UserAnswer> getUserAnswerBySessionId(int id);
    
//    float calcTotalScoreBySessionId(int sessionId);
}
