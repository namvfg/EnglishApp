/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.pojo.UserAnswer;
import com.ndd.repository.UserAnswerRepository;
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
public class UserAnswerServiceImpl implements UserAnswerService {

    @Autowired
    private UserAnswerRepository userAnswerRepo;

    @Override
    public boolean add(UserAnswer userAnswer) {
        return this.userAnswerRepo.add(userAnswer);
    }

    @Override
    public boolean addAll(List<UserAnswer> answers) {
        return this.userAnswerRepo.addAll(answers);
    }

    @Override
    public List<UserAnswer> getUserAnswerBySessionId(int id) {
        return this.userAnswerRepo.getUserAnswerBySessionId(id);
    }

//    @Override
//    public float calcTotalScoreBySessionId(int sessionId) {
//        List<UserAnswer> answers = this.getUserAnswerBySessionId(sessionId);
//        return answers.stream()
//                .map(UserAnswer::getScore)
//                .filter(s -> s != null)
//                .reduce(0f, Float::sum);
//    }
}
