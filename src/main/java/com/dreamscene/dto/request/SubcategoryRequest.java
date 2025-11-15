package com.dreamscene.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubcategoryRequest {
    
    @NotBlank(message = "Subcategory name is required")
    private String name;
    
    @NotNull(message = "Category ID is required")
    private Long categoryId;
    
    private String imageUrl;
}
