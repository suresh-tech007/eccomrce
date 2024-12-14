package com.eccomrce.eccomrce.service;

import java.util.List;

import com.eccomrce.eccomrce.exception.ProductException;
import com.eccomrce.eccomrce.model.Review;
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.request.RevuiewRequest;

public interface ReviewService {
    
    public Review creatReview(RevuiewRequest req,User user) throws ProductException;
    public List<Review> getAllReviews(Long productId);
}
