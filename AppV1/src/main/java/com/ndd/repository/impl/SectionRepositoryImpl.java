/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.repository.impl;

import com.ndd.pojo.Section;
import com.ndd.repository.SectionRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
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
public class SectionRepositoryImpl implements SectionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public List<Section> getSectionsByLessonId(int lessonId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Section> q = b.createQuery(Section.class);
        Root root = q.from(Section.class);
        q.select(root).where(b.equal(root.get("lessonId").get("id"), lessonId));
        return session.createQuery(q).getResultList();
    }

    @Override
    public boolean addOrUpdateSection(Section s) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            if (s.getId() == null) {
                session.persist(s);
                session.flush();
                return true;
            } else {
                session.merge(s);
                session.flush();
                return true;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Section getSectionById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Section.class, id);
    }
}
