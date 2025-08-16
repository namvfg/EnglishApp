/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.repository.impl;

import com.ndd.enums.Skill;
import com.ndd.pojo.LessonType;
import com.ndd.repository.LessonTypeRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public class LessonTypeRepositoryImpl implements LessonTypeRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public List<LessonType> getLessonTypes(Skill skill) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<LessonType> q = b.createQuery(LessonType.class);
        Root root = q.from(LessonType.class);

        if (skill != null) {
            q.select(root).where(b.equal(root.get("skill"), skill));
        } else {
            q.select(root);
        }

        return session.createQuery(q).getResultList();
    }

}
