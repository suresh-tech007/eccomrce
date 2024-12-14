package com.eccomrce.eccomrce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eccomrce.eccomrce.exception.ProductException;
import com.eccomrce.eccomrce.exception.UserException;
import com.eccomrce.eccomrce.model.Review;
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.request.RevuiewRequest;
import com.eccomrce.eccomrce.service.ReviewService;
import com.eccomrce.eccomrce.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 



@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    
        @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody RevuiewRequest req ,@RequestHeader("Authorization") String jwt)throws UserException,ProductException {

        User user = userService.findUserProfileByJwt(jwt);

        Review resReview = reviewService.creatReview(req, user);

         
        
        return new ResponseEntity<>(resReview,HttpStatus.CREATED);
    }
    

    @GetMapping("/product/prodctId")
    public ResponseEntity<List<Review>> getMethodName(@PathVariable Long productId)throws UserException,ProductException {
        List<Review> reviews = reviewService.getAllReviews(productId);
        return new ResponseEntity<>(reviews , HttpStatus.ACCEPTED);
    }
    

}
