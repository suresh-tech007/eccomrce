package com.eccomrce.eccomrce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eccomrce.eccomrce.Config.JwtProvider;
import com.eccomrce.eccomrce.exception.UserException;
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserById(Long userid) throws UserException {

        Optional<User> user = userRepository.findById(userid);
        if (user.isPresent()) {
            return user.get();
            
        }
    throw new UserException("User not found with Id"+ userid);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.gameEmailFromToken(jwt); 
        User user = userRepository.findByEmail(email);

        if(user==null){
            throw new UserException("User not found with email" + email);
        }


        return user;


       
    }

    
}
