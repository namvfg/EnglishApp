/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.repository.impl;

import com.ndd.pojo.User;
import com.ndd.pojo.UserAnswer;
import com.ndd.repository.UserAnswerRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public class UserAnswerRepositoryImpl implements UserAnswerRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public boolean add(UserAnswer userAnswer) {
        Session session = this.factory.getObject().getCurrentSession();
        session.save(userAnswer);
        return true;
    }

    @Override
    public List<UserAnswer> getUserAnswerBySessionId(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<UserAnswer> q = b.createQuery(UserAnswer.class);
        Root root = q.from(UserAnswer.class);
        q.select(root);
        q.where(b.equal(root.get("practiceSessionId").get("id"), id));
        return session.createQuery(q).getResultList();
    }

    @Override
    public boolean addAll(List<UserAnswer> answers) {
        Session session = this.factory.getObject().getCurrentSession();
        for (int i = 0; i < answers.size(); i++) {
            session.save(answers.get(i));
            if (i % 20 == 0) {
                session.flush(); // batch insert tránh full bộ nhớ
            }
        }
        return true;
    }
}
