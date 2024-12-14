package com.eccomrce.eccomrce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eccomrce.eccomrce.model.Order;
import com.eccomrce.eccomrce.model.User;
 

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = 'PLACED'   OR o.orderStatus = 'PENDING'   OR o.orderStatus = 'CONFIRMED' OR o.orderStatus = 'SHIPPED' OR o.orderStatus = 'DELIVERED')")
    public List<Order> getUserOrders(@Param("userId") Long userId);
    

    List<Order> findByUser(User user);

    Order findOrderById(Long orderId);

    List<Order> findByUser(Optional<User> user);

}
