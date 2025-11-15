package com.dreamscene.repository;

import com.dreamscene.entity.Product;
import com.dreamscene.entity.Product.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);
    
    @Query("SELECT p FROM Product p WHERE p.subcategory.id = :subcategoryId")
    List<Product> findBySubcategoryId(@Param("subcategoryId") Long subcategoryId);
    
    List<Product> findByAvailability(Availability availability);
    
    @Query("SELECT p FROM Product p WHERE " +
           "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
           "(:subcategoryId IS NULL OR p.subcategory.id = :subcategoryId) AND " +
           "(:availability IS NULL OR p.availability = :availability)")
    List<Product> findByFilters(
        @Param("categoryId") Long categoryId,
        @Param("subcategoryId") Long subcategoryId,
        @Param("availability") Availability availability
    );
}
