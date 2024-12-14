package com.eccomrce.eccomrce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eccomrce.eccomrce.exception.ProductException;
import com.eccomrce.eccomrce.model.Product;
import com.eccomrce.eccomrce.model.Review;
import com.eccomrce.eccomrce.model.User;
 
import com.eccomrce.eccomrce.repository.ReviewRepository;
import com.eccomrce.eccomrce.request.RevuiewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
  

    @Autowired
    private ProductService productService;

    @Override
    public Review creatReview(RevuiewRequest req, User user) throws ProductException {

        Product product = productService.findProductById(req.getProductId());

        Review review = new Review();

        review.setUser(user);
        review.setProduct(product);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());

 return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviews(Long productId) {
       return reviewRepository.getAllProductsReview(productId);
    }

}
