package com.dreamscene.controller;

import com.dreamscene.dto.request.OrderRequest;
import com.dreamscene.dto.response.ApiResponse;
import com.dreamscene.entity.Order;
import com.dreamscene.entity.Order.OrderStatus;
import com.dreamscene.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    // Public endpoint - for guest checkout
    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<Order>> createOrder(@Valid @RequestBody OrderRequest request) {
        Order order = orderService.createOrder(request);
        return ResponseEntity.ok(ApiResponse.success("Order created successfully", order));
    }
    
    // Public endpoint - for tracking orders by email
    @GetMapping("/orders/email/{email}")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersByEmail(@PathVariable String email) {
        List<Order> orders = orderService.getOrdersByEmail(email);
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orders));
    }
    
    // Public endpoint - for tracking orders by phone
    @GetMapping("/orders/phone/{phone}")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersByPhone(@PathVariable String phone) {
        List<Order> orders = orderService.getOrdersByPhone(phone);
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orders));
    }
    
    // Admin endpoints
    @GetMapping("/admin/orders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orders));
    }
    
    @GetMapping("/admin/orders/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.success("Order retrieved successfully", order));
    }
    
    @PutMapping("/admin/orders/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Order>> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status,
            @RequestParam(required = false) String rejectionReason) {
        Order order = orderService.updateOrderStatus(id, status, rejectionReason);
        return ResponseEntity.ok(ApiResponse.success("Order status updated successfully", order));
    }
}