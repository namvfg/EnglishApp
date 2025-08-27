/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.service.impl;

import com.ndd.pojo.UserWritingAnswer;
import com.ndd.repository.UserWritingAnswerRepository;
import com.ndd.service.UserWritingAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
@Transactional
public class UserWritingAnswerServiceImpl implements UserWritingAnswerService{

    @Autowired
    UserWritingAnswerRepository userWARepository;
    
    @Override
    public boolean addUserWritingAnswer(UserWritingAnswer uwa) {
        return this.userWARepository.addUserWritingAnswer(uwa);
    }

    @Override
    public UserWritingAnswer getUWAById(int id) {
        return this.userWARepository.getUWAById(id);
    }
    
}
