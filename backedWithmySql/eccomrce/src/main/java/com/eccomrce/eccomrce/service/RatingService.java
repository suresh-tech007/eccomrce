package com.eccomrce.eccomrce.service;

import java.util.List;

import com.eccomrce.eccomrce.exception.ProductException;
import com.eccomrce.eccomrce.model.Rating;
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.request.RatingRequest;

public interface RatingService {

    public Rating createRating(RatingRequest req , User user) throws ProductException;

    public List<Rating> getProductsrRatings(Long productId);

    

    


    
}
