/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.repository.impl;

import com.ndd.pojo.Category;
import com.ndd.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
@PropertySource("classpath:configs.properties")
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public List<Category> getCategories(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Category> q = b.createQuery(Category.class);

        Root<Category> root = q.from(Category.class);
        q.select(root);

        //filter
        Predicate[] filters = buildCategoryPredicates(b, root, params);
        if (filters.length > 0) {
            q.where(filters);
        }

        q.orderBy(b.asc(root.get("id")));
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
    public long countCategories(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);

        Root root = q.from(Category.class);
        q.select(b.count(root));

        //filter
        Predicate[] filters = buildCategoryPredicates(b, root, params);
        if (filters.length > 0) {
            q.where(filters);
        }
        return session.createQuery(q).getSingleResult();
    }

    @Override
    public Predicate[] buildCategoryPredicates(CriteriaBuilder b, Root root, Map<String, String> params) {
        List<Predicate> predicates = new ArrayList<>();

        if (params != null) {
            // Filter theo keyword
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), "%" + kw + "%"));
            }

            // Filter theo cateTypeId
            String cateTypeIdStr = params.get("cateTypeId");
            if (cateTypeIdStr != null && !cateTypeIdStr.isEmpty()) {
                int cateTypeId = Integer.parseInt(cateTypeIdStr);
                if (cateTypeId > 0) {
                    predicates.add(
                            b.equal(root.get("categoryTypeId").get("id"), cateTypeId)
                    );
                }
            }
        }

        return predicates.toArray(Predicate[]::new);
    }

    @Override
    public boolean addOrUpdateCategory(Category c) {
        Session session = this.factory.getObject().getCurrentSession();

        try {
            session.merge(c);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }
}
