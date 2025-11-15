package com.dreamscene.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "partners")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partner {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String category;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private String icon;
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @Column(name = "since_year")
    private String since;
    
    @Column
    private String rating;
    
    @Column(length = 500)
    private String gradient;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
