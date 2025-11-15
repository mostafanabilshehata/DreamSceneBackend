package com.dreamscene.dto.request;

import com.dreamscene.entity.OrderItem.OrderType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    
    @NotBlank(message = "User name is required")
    private String userName;
    
    @NotBlank(message = "User email is required")
    @Email(message = "Invalid email format")
    private String userEmail;
    
    @NotBlank(message = "User phone is required")
    private String userPhone;
    
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequest> items;
    
    @Data
    public static class OrderItemRequest {
        @NotNull(message = "Item ID is required")
        private Long itemId;
        
        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        private Integer quantity;
        
        @NotNull(message = "Order type is required")
        private OrderType type;
        
        private Integer rentDays; // Optional, only for RENT type
    }
}
