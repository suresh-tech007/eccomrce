package com.eccomrce.eccomrce.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.eccomrce.eccomrce.exception.OrderException;
import com.eccomrce.eccomrce.model.Address;
import com.eccomrce.eccomrce.model.Cart;
import com.eccomrce.eccomrce.model.CartItem;
import com.eccomrce.eccomrce.model.Order;
import com.eccomrce.eccomrce.model.OrderItem;
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.repository.AdressRepository;
import com.eccomrce.eccomrce.repository.OrderItemRepository;
import com.eccomrce.eccomrce.repository.OrderRepository;
import com.eccomrce.eccomrce.repository.UserRepository;

@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    private AdressRepository adressRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Create a new order for a user
    @Override
    public Order creatOrder (User user, Address shippingAddress) throws OrderException {
        // Check if the address already exists in the database using street, city, and zip code
        Optional<Address> existingAddress = adressRepository.findByStreetAddressAndCityAndZipCode(
                shippingAddress.getStreetAddress(),
                shippingAddress.getCity(),
                shippingAddress.getZipCode()
        );
        
        Address address;
        // If the address does not exist, save the new address
        if (existingAddress.isEmpty()) {
            shippingAddress.setUser(user);  // Link the address to the user
            address = adressRepository.save(shippingAddress);  // Save the new address
        } else {
            // If the address exists, reuse the existing one
            address = existingAddress.get();
        }
    
        // Add the address to the user's address list and save the user (if needed)
        user.getAddress().add(address);
        userRepository.save(user);
    
        // Get the cart associated with the user
        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        
        // Loop through the cart items and create OrderItems
        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());
    
            // Save the order item to the repository and add it to the list
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
    
        // Create the Order object and set its fields
        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotalPrice(cart.getTotalPrice());
        order.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        order.setDiscounted(cart.getDiscount());
        order.setTotalItem(cart.getTotalItem());
        order.setShippingAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setDeliveryDate(LocalDateTime.now().plusDays(7));  // Example: 7 days for delivery
        order.setOrderStatus("PENDING");
        order.getPaymentDetails().setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
    
        // Save the order to the repository
        Order savedOrder = orderRepository.save(order);
    
        // Link each order item to the saved order and save them
        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
    
        // Return the saved order
        return savedOrder;
    }
    

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = finOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");

        return orderRepository.save(order);
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = finOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = finOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = finOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return orderRepository.save(order);
    }

    @Override
    public Order cancledOrder(Long orderId) throws OrderException {
        Order order = finOrderById(orderId);
        order.setOrderStatus("CANCELLED");
        return orderRepository.save(order);
    }

    @Override
    public Order finOrderById(Long orderId) throws OrderException {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }
        throw new OrderException("order not exist with id " + orderId);
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {
      
        List<Order> orders = orderRepository.getUserOrders(userId);

        return orders;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = finOrderById(orderId);
        orderRepository.delete(order);
    }

}
