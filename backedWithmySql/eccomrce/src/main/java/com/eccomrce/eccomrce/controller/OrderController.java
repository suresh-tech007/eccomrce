package com.eccomrce.eccomrce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eccomrce.eccomrce.exception.CartItemException;
import com.eccomrce.eccomrce.exception.OrderException;
import com.eccomrce.eccomrce.exception.UserException;
import com.eccomrce.eccomrce.model.Address;
 
import com.eccomrce.eccomrce.model.Order;
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.repository.AdressRepository;
import com.eccomrce.eccomrce.service.OrderService;
import com.eccomrce.eccomrce.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private UserService userService;

   @Autowired
    private AdressRepository adressRepository;


    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestHeader("Authorization") String jwtToken,
                                                   @RequestBody Address address) throws UserException, CartItemException, OrderException {
        User user = userService.findUserProfileByJwt(jwtToken);
        Order order = orderService.creatOrder(user, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
            
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException {
         User  user = userService.findUserProfileByJwt(jwt);
          
         List<Order> orders = orderService.userOrderHistory(user.getId());

         return new ResponseEntity<>(orders,HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@RequestHeader("Authorization") String jwt , @PathVariable("id") Long orderId ) throws UserException , OrderException {
      userService.findUserProfileByJwt(jwt);
        Order order = orderService.finOrderById(orderId);

        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }
    

    @GetMapping("/getAlladress")
    public List<Address> getMethodName(@RequestHeader("Authorization") String jwtToken) throws UserException {

      User user = userService.findUserProfileByJwt(jwtToken); 

      List<Address> add = adressRepository.findAllByUser(user);


        return add;
    }
    

}

