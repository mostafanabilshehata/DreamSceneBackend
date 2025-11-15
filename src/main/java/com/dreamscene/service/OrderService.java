package com.dreamscene.service;

import com.dreamscene.dto.request.OrderRequest;
import com.dreamscene.entity.Order;
import com.dreamscene.entity.Order.OrderStatus;
import com.dreamscene.entity.OrderItem;
import com.dreamscene.entity.Product;
import com.dreamscene.repository.OrderRepository;
import com.dreamscene.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    
    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }
    
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }
    
    @Transactional
    public Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setUserName(request.getUserName());
        order.setUserEmail(request.getUserEmail());
        order.setUserPhone(request.getUserPhone());
        order.setStatus(OrderStatus.PENDING);
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (OrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getItemId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setType(itemRequest.getType());
            
            BigDecimal itemPrice = itemRequest.getType() == OrderItem.OrderType.SALE 
                    ? product.getSalePrice() 
                    : product.getRentPrice();
            
            orderItem.setPrice(itemPrice);
            
            // Handle rent days for RENT type
            if (itemRequest.getType() == OrderItem.OrderType.RENT && itemRequest.getRentDays() != null) {
                orderItem.setRentDays(itemRequest.getRentDays());
                // Calculate total for rent: price * quantity * days
                totalAmount = totalAmount.add(itemPrice
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()))
                    .multiply(BigDecimal.valueOf(itemRequest.getRentDays())));
            } else {
                // Calculate total for sale: price * quantity
                totalAmount = totalAmount.add(itemPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
            }
            
            orderItem.setOrder(order);
            order.getItems().add(orderItem);
        }
        
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }
    
    public List<Order> getOrdersByEmail(String email) {
        return orderRepository.findByUserEmailOrderByCreatedAtDesc(email);
    }
    
    public List<Order> getOrdersByPhone(String phone) {
        return orderRepository.findByUserPhoneOrderByCreatedAtDesc(phone);
    }
    
    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus status, String rejectionReason) {
        Order order = getOrderById(id);
        order.setStatus(status);
        if (status == OrderStatus.REJECTED && rejectionReason != null) {
            order.setRejectionReason(rejectionReason);
        }
        return orderRepository.save(order);
    }
}