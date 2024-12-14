package com.eccomrce.eccomrce.service;

import com.eccomrce.eccomrce.exception.ProductException;
import com.eccomrce.eccomrce.model.Cart;
import com.eccomrce.eccomrce.model.CartItem;
import com.eccomrce.eccomrce.model.Product;
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.repository.CartRepository;
import com.eccomrce.eccomrce.request.AddItemRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Service;

@Service
public class CartServiceImplemetation implements  CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemSerive cartItemSerive;

    @Autowired
    private ProductService productService;

   @Override
public Cart createCart(User user) {
    Cart cart = new Cart();
    cart.setUser(user);
    cart.setDiscount(0); // Discount field ko default value set kar rahe hain

    return cartRepository.save(cart);
}


    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
       Cart cart = cartRepository.findByUserId(userId);

       Product product = productService.findProductById(req.getProductId());

       CartItem isPresent = cartItemSerive.isCartItemExist(cart, product, req.getSize(), userId);
       if (isPresent==null) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setQuantity( req.getQuantity());
        cartItem.setUserId(userId);
        int price = req.getQuantity()*product.getDiscountedPrice();
        cartItem.setPrice(price);
        cartItem.setSize(req.getSize());
        cartItem.setCreatedAt(LocalDateTime.now());

        CartItem createCartItem = cartItemSerive.creaCartItem(cartItem);

        cart.getCartItems().add(createCartItem);
       Cart c =  cartRepository.save(cart);
        System.out.println(c);
        findUserCart(userId);
       }
       
        
        return "Item Add To Cart";
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
    public Cart getCartByUser(User user) {
      
        return  cartRepository.findByUser(user);  
    }

    @Override
    public double calculateTotalPrice(Cart cart) {
        // Calculate the total price of items in the cart
        return cart.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getPrice() * cartItem.getQuantity()) // Assuming CartItem has a getPrice() method
                .sum(); // Sum the total price
    }

    @Override
    public void clearCart(Cart cart) {
        // Clear the items in the cart and reset total price and item count
        cart.getCartItems().clear(); // Remove all items from the cart
        cart.setTotalPrice(0.0); // Reset total price
        cart.setTotalItem(0); // Reset total item count
        cartRepository.save(cart); // Save the updated cart to the repository
    }
    
}
