package com.eccomrce.eccomrce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eccomrce.eccomrce.exception.ProductException;
import com.eccomrce.eccomrce.exception.UserException;
import com.eccomrce.eccomrce.model.Rating;
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.request.RatingRequest;
import com.eccomrce.eccomrce.service.RatingService;
import com.eccomrce.eccomrce.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 


@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private UserService userService;

    @Autowired 
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> cteateRating(@RequestBody RatingRequest req,@RequestHeader("Authorization") String jwt)throws UserException,ProductException {
      User user = userService.findUserProfileByJwt(jwt);
      Rating rating =ratingService.createRating(req, user);
        
        return new ResponseEntity<Rating>(rating,HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getMethodName(@PathVariable Long productId  ,@RequestHeader("Authorization") String jwt)throws UserException,ProductException {
         userService.findUserProfileByJwt(jwt);
        List<Rating> ratings = ratingService.getProductsrRatings(productId);
        return new ResponseEntity<>(ratings,HttpStatus.CREATED);
    }
    
    
    
}
