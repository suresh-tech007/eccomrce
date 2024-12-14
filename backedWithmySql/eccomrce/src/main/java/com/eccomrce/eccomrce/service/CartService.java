package com.eccomrce.eccomrce.service;

 

import com.eccomrce.eccomrce.exception.ProductException;
import com.eccomrce.eccomrce.model.Cart;
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);

    public  Cart  getCartByUser(User user);

    public double calculateTotalPrice(Cart cart);

    public void clearCart(Cart cart);

}
