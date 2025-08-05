/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.repository.impl;

import com.ndd.enums.Skill;
import com.ndd.pojo.Lesson;
import com.ndd.repository.LessonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public class LessonRepositoryImpl implements LessonRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public Predicate[] buildLessonPredicates(CriteriaBuilder b, Root<Lesson> root, int categoryId, Map<String, String> params) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(root.get("categoryId").get("id"), categoryId));

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("title"), "%" + kw + "%"));
            }
            String skill = params.get("skill");
            if (skill != null && !skill.isEmpty()) {
                try {
                    predicates.add(b.equal(root.get("lessonTypeId").get("skill"), Skill.valueOf(skill)));
                } catch (IllegalArgumentException e) {
                    System.out.println("Skill không hợp lệ: " + skill);
                }
            }
        }
        return predicates.toArray(Predicate[]::new);
    }

    @Override
    public List<Lesson> getLessonsByCategoryId(int categoryId, Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Lesson> q = b.createQuery(Lesson.class);
        Root<Lesson> rL = q.from(Lesson.class);

        Predicate[] filters = buildLessonPredicates(b, rL, categoryId, params);
        q.select(rL).where(filters);
        q.orderBy(b.asc(rL.get("id")));

        Query query = session.createQuery(q);

        //phan trang
        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int pageNum = Integer.parseInt(page);
                int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
                query.setMaxResults(pageSize);
                query.setFirstResult((pageNum - 1) * pageSize);
            }
        }

        return query.getResultList();
    }

    @Override
    public long countLessonsByCategoryId(int categoryId, Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root rL = q.from(Lesson.class);
        q.select(b.count(rL));

        Predicate[] filters = buildLessonPredicates(b, rL, categoryId, params);
        q.where(filters);

        return session.createQuery(q).getSingleResult();
    }
}
