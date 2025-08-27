/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.pojo.UserSpeakingAnswer;
import com.ndd.repository.UserSpeakingAnswerRepository;
import com.ndd.service.UserSpeakingAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
@Transactional
public class UserSpeakingAnswerServiceImpl implements UserSpeakingAnswerService {
    
    @Autowired
    UserSpeakingAnswerRepository userSARepository;

    @Override
    public boolean addUserSpeakingAnswer(UserSpeakingAnswer usa) {
        return this.userSARepository.addUserSpeakingAnswer(usa);
    }

    @Override
    public UserSpeakingAnswer getUSAById(int id) {
        return this.userSARepository.getUSAById(id);
    }

}
