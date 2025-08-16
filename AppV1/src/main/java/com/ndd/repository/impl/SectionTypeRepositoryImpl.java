/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.repository.impl;

import com.ndd.pojo.SectionType;
import com.ndd.repository.SectionTypeRepository;
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
public class SectionTypeRepositoryImpl implements SectionTypeRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public List<SectionType> getSectionTypes() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<SectionType> q = b.createQuery(SectionType.class);
        Root root = q.from(SectionType.class);
        q.select(root);
        return session.createQuery(q).getResultList();
    }

    @Override
    public SectionType getSectionTypeById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(SectionType.class, id);       
    }
}
