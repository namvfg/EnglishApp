/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.repository.impl;

import com.ndd.pojo.PracticeSession;
import com.ndd.pojo.UserAnswer;
import com.ndd.repository.PracticeSessionRepository;
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
public class PracticeSessionRepositoryImpl implements PracticeSessionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public boolean add(PracticeSession practiceSession) {
        Session session = this.factory.getObject().getCurrentSession();
        session.save(practiceSession);
        return true;
    }

    @Override
    public List<PracticeSession> getPracticeSessionByUserId(int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<PracticeSession> q = b.createQuery(PracticeSession.class);
        Root root = q.from(PracticeSession.class);
        q.select(root);
        q.where(b.equal(root.get("userId").get("id"), userId));
        return session.createQuery(q).getResultList();
    }
}
