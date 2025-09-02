/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service;

import com.ndd.pojo.PracticeSession;
import com.ndd.pojo.UserAnswer;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface PracticeSessionService {

    boolean createSessionWithAnswers(PracticeSession session, List<UserAnswer> answers);

    List<PracticeSession> getPracticeSessionByUserId(int userId);
}
