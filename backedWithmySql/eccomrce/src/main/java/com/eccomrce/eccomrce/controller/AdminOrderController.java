package com.eccomrce.eccomrce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eccomrce.eccomrce.exception.OrderException;
import com.eccomrce.eccomrce.model.Order;
import com.eccomrce.eccomrce.response.ApiResponse;
import com.eccomrce.eccomrce.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrderHandler() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<List<Order>>(orders,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<ApiResponse> ConfirmedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
         orderService.confirmedOrder(orderId);
        
    ApiResponse res = new ApiResponse("order confirmed Successfully", "success");
        
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
    @PutMapping("/{orderId}/placed")
    public ResponseEntity<ApiResponse> PlacedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
         orderService.placedOrder(orderId);
        
    ApiResponse res = new ApiResponse("order placed Successfully", "success");
        
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
    @PutMapping("/{orderId}/ship")
    public ResponseEntity<ApiResponse> shipOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
         orderService.shippedOrder(orderId);
        
    ApiResponse res = new ApiResponse("order shipped Successfully", "success");
        
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<ApiResponse> deliverOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
         orderService.deliveredOrder(orderId);
        
    ApiResponse res = new ApiResponse("order delivered Successfully", "success");
        
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse> cancelOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
         orderService.cancledOrder(orderId);
        ApiResponse res = new ApiResponse("order canceled Successfully", "success");
        
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
    @PutMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
         orderService.deleteOrder(orderId);
        ApiResponse res = new ApiResponse("order deleted Successfully", "success");
        
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
    
    


    
}
