package com.dreamscene.service;

import com.dreamscene.dto.request.CategoryRequest;
import com.dreamscene.dto.request.SubcategoryRequest;
import com.dreamscene.entity.Category;
import com.dreamscene.entity.Subcategory;
import com.dreamscene.repository.CategoryRepository;
import com.dreamscene.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }
    
    public List<Subcategory> getSubcategoriesByCategory(Long categoryId) {
        return subcategoryRepository.findByCategoryId(categoryId);
    }
    
    @Transactional
    public Category createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());
        return categoryRepository.save(category);
    }
    
    @Transactional
    public Category updateCategory(Long id, CategoryRequest request) {
        Category category = getCategoryById(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());
        return categoryRepository.save(category);
    }
    
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    
    @Transactional
    public Subcategory createSubcategory(SubcategoryRequest request) {
        Category category = getCategoryById(request.getCategoryId());
        
        Subcategory subcategory = new Subcategory();
        subcategory.setName(request.getName());
        subcategory.setImageUrl(request.getImageUrl());
        subcategory.setCategory(category);
        return subcategoryRepository.save(subcategory);
    }
    
    @Transactional
    public Subcategory updateSubcategory(Long id, SubcategoryRequest request) {
        Subcategory subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found with id: " + id));
        Category category = getCategoryById(request.getCategoryId());
        
        subcategory.setName(request.getName());
        subcategory.setImageUrl(request.getImageUrl());
        subcategory.setCategory(category);
        return subcategoryRepository.save(subcategory);
    }
    
    @Transactional
    public void deleteSubcategory(Long id) {
        subcategoryRepository.deleteById(id);
    }
}