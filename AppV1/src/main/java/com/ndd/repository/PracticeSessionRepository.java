/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.repository;

import com.ndd.pojo.PracticeSession;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface PracticeSessionRepository {

    boolean add(PracticeSession session);
    
    List<PracticeSession> getPracticeSessionByUserId(int userId);
}
