package com.dreamscene.service;

import com.dreamscene.dto.request.ProductRequest;
import com.dreamscene.entity.Category;
import com.dreamscene.entity.Product;
import com.dreamscene.entity.Product.Availability;
import com.dreamscene.entity.Subcategory;
import com.dreamscene.repository.CategoryRepository;
import com.dreamscene.repository.ProductRepository;
import com.dreamscene.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    
    public List<Product> getAllProducts(Long categoryId, Long subcategoryId, Availability availability) {
        if (categoryId != null || subcategoryId != null || availability != null) {
            return productRepository.findByFilters(categoryId, subcategoryId, availability);
        }
        return productRepository.findAll();
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
    
    @Transactional
    public Product createProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId())
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setSalePrice(request.getSalePrice());
        product.setRentPrice(request.getRentPrice());
        product.setAvailability(request.getAvailability());
        product.setImageCover(request.getImageCover());
        product.setRating(request.getRating() != null ? request.getRating() : 0.0);
        product.setStockQuantity(request.getStockQuantity() != null ? request.getStockQuantity() : 0);
        product.setCategory(category);
        product.setSubcategory(subcategory);
        
        return productRepository.save(product);
    }
    
    @Transactional
    public Product updateProduct(Long id, ProductRequest request) {
        Product product = getProductById(id);
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId())
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setSalePrice(request.getSalePrice());
        product.setRentPrice(request.getRentPrice());
        product.setAvailability(request.getAvailability());
        product.setImageCover(request.getImageCover());
        product.setRating(request.getRating());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(category);
        product.setSubcategory(subcategory);
        
        return productRepository.save(product);
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}