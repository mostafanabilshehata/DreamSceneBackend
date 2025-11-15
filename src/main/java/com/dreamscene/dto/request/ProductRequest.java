package com.dreamscene.dto.request;

import com.dreamscene.entity.Product.Availability;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    
    @NotBlank(message = "Product name is required")
    private String name;
    
    private String description;
    
    @Positive(message = "Sale price must be positive")
    private BigDecimal salePrice;
    
    @Positive(message = "Rent price must be positive")
    private BigDecimal rentPrice;
    
    @NotNull(message = "Availability is required")
    private Availability availability;
    
    private String imageCover;
    
    private Double rating;
    
    private Integer stockQuantity;
    
    @NotNull(message = "Category ID is required")
    private Long categoryId;
    
    @NotNull(message = "Subcategory ID is required")
    private Long subcategoryId;
}
