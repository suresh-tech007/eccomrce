package com.eccomrce.eccomrce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eccomrce.eccomrce.model.Cart;
import com.eccomrce.eccomrce.model.User;

public interface CartRepository extends JpaRepository<Cart , Long> {
    
    @Query("SELECT c from Cart c WHERE c.user.id =  :userId")
    public Cart findByUserId(@Param("userId")Long userId);

    public Cart findByUser(User user);
}
