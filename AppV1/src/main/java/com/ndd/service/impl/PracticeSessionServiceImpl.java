/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.pojo.PracticeSession;
import com.ndd.pojo.UserAnswer;
import com.ndd.repository.PracticeSessionRepository;
import com.ndd.service.PracticeSessionService;
import com.ndd.service.UserAnswerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
@Transactional
public class PracticeSessionServiceImpl implements PracticeSessionService {

    @Autowired
    private PracticeSessionRepository practiceSessionRepo;
    @Autowired
    private UserAnswerService userAnswerService;

    @Override
    public boolean createSessionWithAnswers(PracticeSession session, List<UserAnswer> answers) {
        if (answers == null || answers.isEmpty()) {
            return false;
        }
        boolean sessionAdded = this.practiceSessionRepo.add(session);
        if (!sessionAdded) {
            return false;
        }
        for (UserAnswer ua : answers) {
            ua.setPracticeSessionId(session);
        }
        return this.userAnswerService.addAll(answers);
    }

    @Override
    public List<PracticeSession> getPracticeSessionByUserId(int userId) {
        return this.practiceSessionRepo.getPracticeSessionByUserId(userId);
    }

}
