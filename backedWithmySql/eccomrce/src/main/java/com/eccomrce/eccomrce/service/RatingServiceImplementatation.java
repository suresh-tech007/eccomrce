package com.eccomrce.eccomrce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eccomrce.eccomrce.exception.ProductException;
import com.eccomrce.eccomrce.model.Product;
import com.eccomrce.eccomrce.model.Rating;
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.repository.RatingRepository;
import com.eccomrce.eccomrce.request.RatingRequest;

@Service
public class RatingServiceImplementatation implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
         Product product = productService.findProductById(req.getProductId());

         Rating rating = new Rating();
         rating.setProduct(product);
         rating.setUser(user);
         rating.setRating(req.getRaing());
         rating.setCreatedAt(LocalDateTime.now());

         return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsrRatings(Long productId) {
        return ratingRepository.getAllProductRating(productId);
    }
    
}
