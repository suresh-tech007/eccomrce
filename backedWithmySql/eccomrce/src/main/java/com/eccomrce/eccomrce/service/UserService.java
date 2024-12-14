package com.eccomrce.eccomrce.service;

import org.springframework.stereotype.Service;

import com.eccomrce.eccomrce.exception.UserException;
import com.eccomrce.eccomrce.model.User;

@Service
public interface UserService {

    
 

    public User findUserById(Long userid) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
    
}
