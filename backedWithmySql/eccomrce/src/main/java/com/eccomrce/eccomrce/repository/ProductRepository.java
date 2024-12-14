package com.eccomrce.eccomrce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eccomrce.eccomrce.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

        @Query("SELECT p FROM Product p " +
                        "WHERE (:category IS NULL OR p.category.name = :category) " +
                        "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) "
                        +
                        "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount) " +
                        "ORDER BY " +
                        "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
                        "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")
        List<Product> filterProducts(@Param("category") String category,
                        @Param("minPrice") Integer minPrice,
                        @Param("maxPrice") Integer maxPrice,
                        @Param("minDiscount") Integer minDiscount,
                        @Param("sort") String sort);

        List<Product> findByTitleContainingIgnoreCase(String title);

        // Using custom query
        @Query("SELECT p FROM Product p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :name, '%'))")
        List<Product> searchByName(@Param("name") String name);

        @Query("SELECT p FROM Product p " +
        "WHERE (:category IS NULL OR p.category.name = :category) " )
        List<Product> findByCategory(@Param("category") String category);


        // @Query("SELECT p FROM Product p " +
        // "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) "
        // +
        // "AND (:category IS NULL OR p.category.name = :category) " +
        // "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN
        // :minPrice AND :maxPrice)) " +
        // "ORDER BY p.name ASC")
        // List<Product> searchProducts(@Param("name") String name,
        // @Param("category") String category,
        // @Param("minPrice") Integer minPrice,
        // @Param("maxPrice") Integer maxPrice);
}
