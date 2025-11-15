# 🚀 DreamScene Backend - FINAL SETUP GUIDE

## ✅ Files Already Created

The following files have been successfully created:
- ✅ pom.xml
- ✅ application.properties
- ✅ DreamSceneApplication.java
- ✅ All 8 Entity classes
- ✅ All 6 Repository interfaces
- ✅ All DTO Request/Response classes
- ✅ JwtUtil.java
- ✅ SecurityConfig.java
- ✅ JwtAuthenticationFilter.java

## 📝 Remaining Files to Create

You need to create the following files manually in your IDE:

### 1. UserDetailsServiceImpl.java
**Path:** `src/main/java/com/DreamScene/service/UserDetailsServiceImpl.java`

```java
package com.DreamScene.service;

import com.DreamScene.entity.User;
import com.DreamScene.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
```

### 2. AuthService.java
**Path:** `src/main/java/com/DreamScene/service/AuthService.java`

```java
package com.DreamScene.service;

import com.DreamScene.dto.request.LoginRequest;
import com.DreamScene.dto.response.LoginResponse;
import com.DreamScene.entity.User;
import com.DreamScene.repository.UserRepository;
import com.DreamScene.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return new LoginResponse(token, user.getUsername(), user.getEmail(), user.getRole().name());
    }
}
```

### 3. CategoryService.java
**Path:** `src/main/java/com/DreamScene/service/CategoryService.java`

```java
package com.DreamScene.service;

import com.DreamScene.dto.request.CategoryRequest;
import com.DreamScene.dto.request.SubcategoryRequest;
import com.DreamScene.entity.Category;
import com.DreamScene.entity.Subcategory;
import com.DreamScene.repository.CategoryRepository;
import com.DreamScene.repository.SubcategoryRepository;
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
```

### 4. ProductService.java
**Path:** `src/main/java/com/DreamScene/service/ProductService.java`

```java
package com.DreamScene.service;

import com.DreamScene.dto.request.ProductRequest;
import com.DreamScene.entity.Category;
import com.DreamScene.entity.Product;
import com.DreamScene.entity.Product.Availability;
import com.DreamScene.entity.Subcategory;
import com.DreamScene.repository.CategoryRepository;
import com.DreamScene.repository.ProductRepository;
import com.DreamScene.repository.SubcategoryRepository;
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
```

### 5. PartnerService.java
**Path:** `src/main/java/com/DreamScene/service/PartnerService.java`

```java
package com.DreamScene.service;

import com.DreamScene.dto.request.PartnerRequest;
import com.DreamScene.entity.Partner;
import com.DreamScene.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerService {
    
    private final PartnerRepository partnerRepository;
    
    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }
    
    public Partner getPartnerById(Long id) {
        return partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found with id: " + id));
    }
    
    @Transactional
    public Partner createPartner(PartnerRequest request) {
        Partner partner = new Partner();
        partner.setTitle(request.getTitle());
        partner.setCategory(request.getCategory());
        partner.setDescription(request.getDescription());
        partner.setIcon(request.getIcon());
        partner.setSince(request.getSince());
        partner.setRating(request.getRating());
        partner.setGradient(request.getGradient());
        return partnerRepository.save(partner);
    }
    
    @Transactional
    public Partner updatePartner(Long id, PartnerRequest request) {
        Partner partner = getPartnerById(id);
        partner.setTitle(request.getTitle());
        partner.setCategory(request.getCategory());
        partner.setDescription(request.getDescription());
        partner.setIcon(request.getIcon());
        partner.setSince(request.getSince());
        partner.setRating(request.getRating());
        partner.setGradient(request.getGradient());
        return partnerRepository.save(partner);
    }
    
    @Transactional
    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }
}
```

### 6. OrderService.java
**Path:** `src/main/java/com/DreamScene/service/OrderService.java`

```java
package com.DreamScene.service;

import com.DreamScene.dto.request.OrderRequest;
import com.DreamScene.entity.Order;
import com.DreamScene.entity.Order.OrderStatus;
import com.DreamScene.entity.OrderItem;
import com.DreamScene.entity.Product;
import com.DreamScene.repository.OrderRepository;
import com.DreamScene.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    
    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }
    
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }
    
    @Transactional
    public Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setUserName(request.getUserName());
        order.setUserEmail(request.getUserEmail());
        order.setUserPhone(request.getUserPhone());
        order.setStatus(OrderStatus.PENDING);
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (OrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setType(itemRequest.getType());
            
            BigDecimal itemPrice = itemRequest.getType() == OrderItem.OrderType.SALE 
                    ? product.getSalePrice() 
                    : product.getRentPrice();
            
            orderItem.setPrice(itemPrice);
            orderItem.setOrder(order);
            
            order.getItems().add(orderItem);
            totalAmount = totalAmount.add(itemPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }
        
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }
    
    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
```

---

## Continue with Controllers in next message...
