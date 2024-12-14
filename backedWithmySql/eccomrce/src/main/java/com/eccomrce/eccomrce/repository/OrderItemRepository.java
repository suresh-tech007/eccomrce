package com.eccomrce.eccomrce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eccomrce.eccomrce.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem , Long>{
   
    
}
