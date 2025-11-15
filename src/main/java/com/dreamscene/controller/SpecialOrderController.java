package com.dreamscene.controller;

import com.dreamscene.dto.request.SpecialOrderRequest;
import com.dreamscene.dto.response.ApiResponse;
import com.dreamscene.entity.SpecialOrder;
import com.dreamscene.service.SpecialOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SpecialOrderController {
    
    private final SpecialOrderService specialOrderService;
    
    // Public endpoint - create special order
    @PostMapping("/special-orders")
    public ResponseEntity<ApiResponse<SpecialOrder>> createSpecialOrder(@Valid @RequestBody SpecialOrderRequest request) {
        SpecialOrder specialOrder = specialOrderService.createSpecialOrder(request);
        return ResponseEntity.ok(ApiResponse.success("Special order submitted successfully", specialOrder));
    }
    
    // Public endpoint - get user's special orders by email
    @GetMapping("/special-orders/user/{email}")
    public ResponseEntity<ApiResponse<List<SpecialOrder>>> getUserSpecialOrders(@PathVariable String email) {
        List<SpecialOrder> specialOrders = specialOrderService.getSpecialOrdersByEmail(email);
        return ResponseEntity.ok(ApiResponse.success("Special orders retrieved successfully", specialOrders));
    }
    
    // Admin endpoints
    @GetMapping("/admin/special-orders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<SpecialOrder>>> getAllSpecialOrders() {
        List<SpecialOrder> specialOrders = specialOrderService.getAllSpecialOrders();
        return ResponseEntity.ok(ApiResponse.success("Special orders retrieved successfully", specialOrders));
    }
    
    @GetMapping("/admin/special-orders/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<SpecialOrder>> getSpecialOrderById(@PathVariable Long id) {
        SpecialOrder specialOrder = specialOrderService.getSpecialOrderById(id);
        return ResponseEntity.ok(ApiResponse.success("Special order retrieved successfully", specialOrder));
    }
    
    @PutMapping("/admin/special-orders/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<SpecialOrder>> updateSpecialOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        SpecialOrder.OrderStatus status = SpecialOrder.OrderStatus.valueOf(request.get("status"));
        String adminNotes = request.get("adminNotes");
        SpecialOrder specialOrder = specialOrderService.updateStatus(id, status, adminNotes);
        return ResponseEntity.ok(ApiResponse.success("Special order status updated successfully", specialOrder));
    }
    
    @DeleteMapping("/admin/special-orders/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteSpecialOrder(@PathVariable Long id) {
        specialOrderService.deleteSpecialOrder(id);
        return ResponseEntity.ok(ApiResponse.success("Special order deleted successfully", null));
    }
}
