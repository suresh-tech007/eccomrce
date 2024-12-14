package com.eccomrce.eccomrce.service;

import java.util.List;

import com.eccomrce.eccomrce.exception.OrderException;
import com.eccomrce.eccomrce.model.Address;
import com.eccomrce.eccomrce.model.Order;
import com.eccomrce.eccomrce.model.User;

public interface OrderService  {

    public Order creatOrder(User user, Address shippingAddress) throws OrderException;

    public Order finOrderById(Long orderId) throws OrderException ;

    public List<Order> userOrderHistory(Long userId)  ;

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;
    
    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;


    public Order cancledOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;
}
