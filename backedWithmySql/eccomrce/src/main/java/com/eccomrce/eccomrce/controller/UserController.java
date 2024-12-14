package com.eccomrce.eccomrce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eccomrce.eccomrce.exception.UserException;
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // User को ID के आधार पर प्राप्त करने के लिए GET मैपिंग
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.findUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // JWT के आधार पर प्रोफाइल प्राप्त करने के लिए GET मैपिंग
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileByJwt(@RequestHeader("Authorization") String jwtToken) {
        try {
            // Authorization header में आने वाले "Bearer " prefix को हटाएं
            String token = jwtToken.substring(7);

            User user = userService.findUserProfileByJwt(token);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
