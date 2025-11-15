package com.dreamscene.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SpecialOrderRequest {
    
    @NotBlank(message = "User name is required")
    private String userName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String userEmail;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    private String imageUrls; // Comma-separated URLs
}
