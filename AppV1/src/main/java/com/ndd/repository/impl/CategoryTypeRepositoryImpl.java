/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.repository.impl;

import com.ndd.pojo.CategoryType;
import com.ndd.repository.CategoryTypeRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public class CategoryTypeRepositoryImpl implements CategoryTypeRepository{

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<CategoryType> getCategoryTypes() {
        Session session = this.factory.getObject().openSession();
        Query query = session.createNamedQuery("CategoryType.findAll", CategoryType.class);
        return query.getResultList();
    }
}
