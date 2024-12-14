package com.eccomrce.eccomrce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eccomrce.eccomrce.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    @Query("SELECT r FROM Review r WHERE r.product.id = :productId") // Ensure that the entity is correctly referenced
    List<Review> getAllProductsReview(@Param("productId") Long productId);
}
