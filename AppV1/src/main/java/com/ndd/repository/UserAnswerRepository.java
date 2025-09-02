/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.repository;

import com.ndd.pojo.UserAnswer;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface UserAnswerRepository {

    boolean add(UserAnswer userAnswer);

    boolean addAll(List<UserAnswer> answers);

    List<UserAnswer> getUserAnswerBySessionId(int id);
}
