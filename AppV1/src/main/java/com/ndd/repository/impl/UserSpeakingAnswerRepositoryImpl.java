/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.repository.impl;

import com.ndd.pojo.UserSpeakingAnswer;
import com.ndd.pojo.UserWritingAnswer;
import com.ndd.repository.UserSpeakingAnswerRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public class UserSpeakingAnswerRepositoryImpl implements UserSpeakingAnswerRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public boolean addUserSpeakingAnswer(UserSpeakingAnswer usa) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.save(usa);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public UserSpeakingAnswer getUSAById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(UserSpeakingAnswer.class, id);
    }

}
