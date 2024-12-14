package com.eccomrce.eccomrce.controller;

import com.eccomrce.eccomrce.exception.CartItemException;
import com.eccomrce.eccomrce.exception.ProductException;
import com.eccomrce.eccomrce.exception.UserException;
import com.eccomrce.eccomrce.model.Cart;
 
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.repository.CartRepository;
import com.eccomrce.eccomrce.request.AddItemRequest;
import com.eccomrce.eccomrce.request.updateCartItemrequest;
import com.eccomrce.eccomrce.response.ApiResponse;
import com.eccomrce.eccomrce.service.CartItemSerive;
import com.eccomrce.eccomrce.service.CartService;
 
import com.eccomrce.eccomrce.service.UserService;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemSerive cartItemSerive;

    @Autowired
    private UserService userService;

 

    @Autowired
    private CartRepository cartRepository;

    // Endpoint to get the cart by user
    @GetMapping("/")
    public ResponseEntity<Cart> getCartByUser(@RequestHeader("Authorization") String jwtToken) throws UserException {
        User user = userService.findUserProfileByJwt(jwtToken);
        Cart cart = cartRepository.findByUser(user);
       
    

        return new ResponseEntity<>(cart,HttpStatus.OK); // Return the cart with sorted items
    }

    @PutMapping("/addItem")
    public ResponseEntity<ApiResponse> addCartItem(@RequestHeader("Authorization") String jwt,
            @RequestBody AddItemRequest product) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
         
        Cart cart = cartRepository.findByUserId(user.getId());
        if(cart == null){
            cart = cartService.createCart(user);
           }
    
        String updatedCart = cartService.addCartItem(user.getId(), product);
        ApiResponse response = new ApiResponse(updatedCart, "SUCCESS");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@RequestHeader("Authorization") String jwtToken,
            @PathVariable Long productId) throws UserException, ProductException, CartItemException {

        User user = userService.findUserProfileByJwt(jwtToken);

        String updatedCart = cartItemSerive.removeCartItem(user.getId(), productId);

        ApiResponse response = new ApiResponse(updatedCart, "SUCCESS");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateCartItem(@RequestHeader("Authorization") String jwtToken,
            @PathVariable Long productId, @RequestBody updateCartItemrequest cartItemdata)
            throws UserException, ProductException, CartItemException {

        User user = userService.findUserProfileByJwt(jwtToken);

        cartItemSerive.updCartItem(user.getId(), productId, cartItemdata);

        ApiResponse response = new ApiResponse("updatedCart", "SUCCESS");

        return ResponseEntity.ok(response);
    }

}
