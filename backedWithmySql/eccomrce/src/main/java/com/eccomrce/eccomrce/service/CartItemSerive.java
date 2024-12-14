package com.eccomrce.eccomrce.service;

import com.eccomrce.eccomrce.exception.CartItemException;
import com.eccomrce.eccomrce.exception.UserException;
import com.eccomrce.eccomrce.model.Cart;
import com.eccomrce.eccomrce.model.CartItem;
import com.eccomrce.eccomrce.model.Product;
import com.eccomrce.eccomrce.request.updateCartItemrequest;

public interface CartItemSerive {


    public CartItem creaCartItem(CartItem cartItem);

    public CartItem updCartItem(Long userid,Long id,updateCartItemrequest cartItem ) throws CartItemException,UserException;

    public CartItem isCartItemExist(Cart cart , Product product , String size , Long userId);

    public Cart findUserCart(Long userId);

    public String removeCartItem(Long userId , Long cartItemId )  throws CartItemException,UserException;

    public CartItem findCartItemById(Long cartItemId)  throws CartItemException;

     

}
