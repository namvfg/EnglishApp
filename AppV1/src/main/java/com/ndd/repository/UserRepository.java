/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ndd.repository;

import com.ndd.pojo.User;

/**
 *
 * @author Admin
 */
public interface UserRepository {
    User getUserByEmail(String email);
    
    boolean authUser(String email, String password);
    
    boolean addUser(User user);
    
    User getUserById(int id);
}
