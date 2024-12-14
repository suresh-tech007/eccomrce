package com.eccomrce.eccomrce.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eccomrce.eccomrce.exception.OrderException;
import com.eccomrce.eccomrce.exception.UserException;
import com.eccomrce.eccomrce.model.Order;
import com.eccomrce.eccomrce.repository.OrderRepository;
import com.eccomrce.eccomrce.service.OrderService;
import com.eccomrce.eccomrce.service.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.eccomrce.eccomrce.response.ApiResponse;
import com.eccomrce.eccomrce.response.PaymentLinkResponse;

@RestController
@RequestMapping("/api")
public class PaymentController {
    @Value("${razorpay.api.key}")
    String apikey;

    @Value("${razorpay.api.secret}")
    String apiSecret;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
    @RequestHeader("Authorization") String jwtToken) throws OrderException, RazorpayException, UserException {

        userService.findUserProfileByJwt(jwtToken);

        Order order = orderService.finOrderById(orderId);

        try {
            RazorpayClient razorpay = new RazorpayClient(apikey, apiSecret);
        
            JSONObject paymentLinkRequest = new JSONObject();
            
            // Required fields
            paymentLinkRequest.put("amount", order.getTotalPrice() * 100); // Amount in paise
            paymentLinkRequest.put("currency", "INR");
        
            // Customer details
            JSONObject customer = new JSONObject();
            customer.put("name", order.getUser().getFirstName());
            customer.put("email", order.getUser().getEmail());
        
            paymentLinkRequest.put("customer", customer);
        
            // Notification preferences
            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
        
            paymentLinkRequest.put("notify", notify);
        
            // Callback URL
            paymentLinkRequest.put("callback_url", "http://localhost:5173/payment/" + orderId);
            paymentLinkRequest.put("callback_method", "get");
        
            // Create payment link
            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
        
            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");
        
            PaymentLinkResponse res = new PaymentLinkResponse();
            res.setPayment_link_id(paymentLinkId);
            res.setPayment_link_url(paymentLinkUrl);
        
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        
        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }
        

    }

    @GetMapping("/payment")
    public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId,
            @RequestParam(name = "order_id") Long orderId) throws OrderException, RazorpayException {
        Order order = orderService.finOrderById(orderId);
        RazorpayClient razorpay = new RazorpayClient(apikey, apiSecret);

        try {
            Payment payment = razorpay.payments.fetch(paymentId);
            System.out.println(payment);
             
            if (payment.get("status").equals("captured")) {
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setPaymentMethod(payment.get("method"));
                order.getPaymentDetails().setRazorpayPaymentId(paymentId); 
                order.getPaymentDetails().setRazorpayPaymentLinkStatus(payment.get("status"));
                order.getPaymentDetails().setStatus("COMPLETED");
                order.setOrderStatus("PLACED");
                orderRepository.save(order);
            }

            ApiResponse res = new ApiResponse();
            res.setMessage("you order get placed");
            res.setStatus("success");

            return new ResponseEntity<ApiResponse>(res, HttpStatus.CREATED);

        } catch (Exception e) {
            System.out.println(e);
            throw new RazorpayException(e.getMessage());
        }
    }

}
