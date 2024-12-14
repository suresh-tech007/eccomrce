package com.eccomrce.eccomrce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eccomrce.eccomrce.exception.CartItemException;
import com.eccomrce.eccomrce.exception.UserException;
import com.eccomrce.eccomrce.model.Cart;
import com.eccomrce.eccomrce.model.CartItem;
import com.eccomrce.eccomrce.model.Product;
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.repository.CartItemRepository;
import com.eccomrce.eccomrce.repository.CartRepository;
import com.eccomrce.eccomrce.request.updateCartItemrequest;

@Service
public class CartItemServiceImplementation implements CartItemSerive {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService; 

    @Autowired 
    private CartRepository cartRepository ;



    @Override
    public CartItem creaCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
        CartItem createdCartItem = cartItemRepository.save(cartItem);

        return createdCartItem;
    }

    @Override
    public CartItem updCartItem(Long userid, Long id, updateCartItemrequest cartItem) throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());

        if (user.getId().equals(userid)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
            CartItem UpdatedCartItem = cartItemRepository.save(item);

            findUserCart(userid);
            return UpdatedCartItem;

        } else {
            throw new CartItemException("UnAuthorized ");
        }

    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart= cartRepository.findByUserId(userId);

        int totalPrice =0;
        int totalDiscountedPrice =0;
        int totalItem =0;

        for (CartItem cartitem : cart.getCartItems()) {
            totalPrice = totalPrice + cartitem.getPrice();
            totalDiscountedPrice = totalDiscountedPrice + cartitem.getDiscountedPrice();
            totalItem = totalItem + cartitem.getQuantity();
            
        }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setDiscount(totalPrice - totalDiscountedPrice);
        
        return cartRepository.save(cart);

    }
    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {

        CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);

        return cartItem;

    }

    @Override
    public String removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

        CartItem cartitem = findCartItemById(cartItemId);
        User user = userService.findUserById(cartitem.getUserId());
        User requsr = userService.findUserById(userId);

        if (user.getId().equals(requsr.getId())) {
            cartItemRepository.deleteById(cartItemId);

            findUserCart(userId);
            return "Cartitem delete successfully";
        } else {
            throw new UserException("you can't remove another User's Item ");
        }

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

        if (opt.isPresent()) {
            return opt.get();
        }
        throw new CartItemException("cartItem not found with Id : " + cartItemId);

    }
}