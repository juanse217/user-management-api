package com.sebastian.usermanagement.repository;

import java.util.Collection;
import java.util.Optional;

import com.sebastian.usermanagement.model.User;

public interface UserRepository {

    Optional<User> save(User user);
    Collection<User> findAll(); //Only for admin role
    Optional<User> findById(Long id); //Admin, in service will require password to check if passed is the same as the returned User's. 
    void delete(Long id); //Admin, same as previous method for standard. 
    boolean exists(Long id);//we add this method with the purpose of previous projects. It's easier and faster to check instead of returning the full object. 
} 
