package com.dreamscene.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 2000)
    private String description;
    
    @Column(name = "sale_price", precision = 10, scale = 2)
    private BigDecimal salePrice;
    
    @Column(name = "rent_price", precision = 10, scale = 2)
    private BigDecimal rentPrice;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Availability availability;
    
    @Column(name = "image_cover")
    private String imageCover;
    
    @Column
    private Double rating = 0.0;
    
    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id", nullable = false)
    @JsonIgnore
    private Subcategory subcategory;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images;
    
    // Helper methods to get IDs for JSON serialization
    public Long getCategoryId() {
        return category != null ? category.getId() : null;
    }
    
    public Long getSubcategoryId() {
        return subcategory != null ? subcategory.getId() : null;
    }
    
    public enum Availability {
        SALE, RENT, BOTH
    }
}
