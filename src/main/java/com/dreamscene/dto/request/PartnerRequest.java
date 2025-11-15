package com.dreamscene.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PartnerRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    private String description;
    
    private String icon;
    
    private String imageUrl;
    
    private String since;
    
    private String rating;
    
    private String gradient;
}
