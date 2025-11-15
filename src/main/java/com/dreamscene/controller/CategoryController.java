package com.dreamscene.controller;

import com.dreamscene.dto.request.CategoryRequest;
import com.dreamscene.dto.request.SubcategoryRequest;
import com.dreamscene.dto.response.ApiResponse;
import com.dreamscene.entity.Category;
import com.dreamscene.entity.Subcategory;
import com.dreamscene.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    // Public endpoints
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.success("Categories retrieved successfully", categories));
    }
    
    @GetMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(ApiResponse.success("Category retrieved successfully", category));
    }
    
    @GetMapping("/categories/{id}/subcategories")
    public ResponseEntity<ApiResponse<List<Subcategory>>> getSubcategoriesByCategory(@PathVariable Long id) {
        List<Subcategory> subcategories = categoryService.getSubcategoriesByCategory(id);
        return ResponseEntity.ok(ApiResponse.success("Subcategories retrieved successfully", subcategories));
    }
    
    // Admin endpoints
    @PostMapping("/admin/categories")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Category>> createCategory(@Valid @RequestBody CategoryRequest request) {
        Category category = categoryService.createCategory(request);
        return ResponseEntity.ok(ApiResponse.success("Category created successfully", category));
    }
    
    @PutMapping("/admin/categories/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Category>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {
        Category category = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(ApiResponse.success("Category updated successfully", category));
    }
    
    @DeleteMapping("/admin/categories/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", null));
    }
    
    @PostMapping("/admin/subcategories")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Subcategory>> createSubcategory(@Valid @RequestBody SubcategoryRequest request) {
        Subcategory subcategory = categoryService.createSubcategory(request);
        return ResponseEntity.ok(ApiResponse.success("Subcategory created successfully", subcategory));
    }
    
    @PutMapping("/admin/subcategories/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Subcategory>> updateSubcategory(
            @PathVariable Long id,
            @Valid @RequestBody SubcategoryRequest request) {
        Subcategory subcategory = categoryService.updateSubcategory(id, request);
        return ResponseEntity.ok(ApiResponse.success("Subcategory updated successfully", subcategory));
    }
    
    @DeleteMapping("/admin/subcategories/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteSubcategory(@PathVariable Long id) {
        categoryService.deleteSubcategory(id);
        return ResponseEntity.ok(ApiResponse.success("Subcategory deleted successfully", null));
    }
}