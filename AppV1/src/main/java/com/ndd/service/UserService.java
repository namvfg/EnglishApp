/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ndd.service;

import com.ndd.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Admin
 */
public interface UserService extends UserDetailsService{
   boolean authUser(String email, String password);
   
   boolean addUser(User user);
   
   User getUserByEmail(String email);
   
   User getUserById(int id);
}
