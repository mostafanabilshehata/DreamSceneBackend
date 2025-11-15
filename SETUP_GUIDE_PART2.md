# 🚀 DreamScene Backend - FINAL SETUP GUIDE (Part 2)

## REST Controllers

### 1. AuthController.java
**Path:** `src/main/java/com/DreamScene/controller/AuthController.java`

```java
package com.DreamScene.controller;

import com.DreamScene.dto.request.LoginRequest;
import com.DreamScene.dto.response.ApiResponse;
import com.DreamScene.dto.response.LoginResponse;
import com.DreamScene.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(ApiResponse.success("Login successful", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Invalid username or password"));
        }
    }
}
```

### 2. CategoryController.java
**Path:** `src/main/java/com/DreamScene/controller/CategoryController.java`

```java
package com.DreamScene.controller;

import com.DreamScene.dto.request.CategoryRequest;
import com.DreamScene.dto.request.SubcategoryRequest;
import com.DreamScene.dto.response.ApiResponse;
import com.DreamScene.entity.Category;
import com.DreamScene.entity.Subcategory;
import com.DreamScene.service.CategoryService;
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
```

### 3. ProductController.java
**Path:** `src/main/java/com/DreamScene/controller/ProductController.java`

```java
package com.DreamScene.controller;

import com.DreamScene.dto.request.ProductRequest;
import com.DreamScene.dto.response.ApiResponse;
import com.DreamScene.entity.Product;
import com.DreamScene.entity.Product.Availability;
import com.DreamScene.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    
    // Public endpoints
    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long subcategoryId,
            @RequestParam(required = false) Availability availability) {
        List<Product> products = productService.getAllProducts(categoryId, subcategoryId, availability);
        return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", products));
    }
    
    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success("Product retrieved successfully", product));
    }
    
    // Admin endpoints
    @PostMapping("/admin/products")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = productService.createProduct(request);
        return ResponseEntity.ok(ApiResponse.success("Product created successfully", product));
    }
    
    @PutMapping("/admin/products/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Product>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        Product product = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully", product));
    }
    
    @DeleteMapping("/admin/products/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully", null));
    }
}
```

### 4. PartnerController.java
**Path:** `src/main/java/com/DreamScene/controller/PartnerController.java`

```java
package com.DreamScene.controller;

import com.DreamScene.dto.request.PartnerRequest;
import com.DreamScene.dto.response.ApiResponse;
import com.DreamScene.entity.Partner;
import com.DreamScene.service.PartnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PartnerController {
    
    private final PartnerService partnerService;
    
    // Public endpoint
    @GetMapping("/partners")
    public ResponseEntity<ApiResponse<List<Partner>>> getAllPartners() {
        List<Partner> partners = partnerService.getAllPartners();
        return ResponseEntity.ok(ApiResponse.success("Partners retrieved successfully", partners));
    }
    
    @GetMapping("/partners/{id}")
    public ResponseEntity<ApiResponse<Partner>> getPartnerById(@PathVariable Long id) {
        Partner partner = partnerService.getPartnerById(id);
        return ResponseEntity.ok(ApiResponse.success("Partner retrieved successfully", partner));
    }
    
    // Admin endpoints
    @PostMapping("/admin/partners")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Partner>> createPartner(@Valid @RequestBody PartnerRequest request) {
        Partner partner = partnerService.createPartner(request);
        return ResponseEntity.ok(ApiResponse.success("Partner created successfully", partner));
    }
    
    @PutMapping("/admin/partners/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Partner>> updatePartner(
            @PathVariable Long id,
            @Valid @RequestBody PartnerRequest request) {
        Partner partner = partnerService.updatePartner(id, request);
        return ResponseEntity.ok(ApiResponse.success("Partner updated successfully", partner));
    }
    
    @DeleteMapping("/admin/partners/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.ok(ApiResponse.success("Partner deleted successfully", null));
    }
}
```

### 5. OrderController.java
**Path:** `src/main/java/com/DreamScene/controller/OrderController.java`

```java
package com.DreamScene.controller;

import com.DreamScene.dto.request.OrderRequest;
import com.DreamScene.dto.response.ApiResponse;
import com.DreamScene.entity.Order;
import com.DreamScene.entity.Order.OrderStatus;
import com.DreamScene.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    // Public endpoint - for guest checkout
    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<Order>> createOrder(@Valid @RequestBody OrderRequest request) {
        Order order = orderService.createOrder(request);
        return ResponseEntity.ok(ApiResponse.success("Order created successfully", order));
    }
    
    // Admin endpoints
    @GetMapping("/admin/orders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orders));
    }
    
    @GetMapping("/admin/orders/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.success("Order retrieved successfully", order));
    }
    
    @PutMapping("/admin/orders/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Order>> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        Order order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Order status updated successfully", order));
    }
}
```

---

## 🗄️ Database Initialization Script

### data.sql
**Path:** `src/main/resources/data.sql`

```sql
-- Insert admin user (password: admin123)
INSERT INTO users (username, email, password, role) 
VALUES ('admin', 'admin@DreamScene.com', '$2a$10$dX1J0dQpLw9XpJ1K5gJJ6OQ5VZ5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5', 'ADMIN')
ON DUPLICATE KEY UPDATE username = username;

-- Insert categories
INSERT INTO categories (name, description, image_url) 
VALUES 
    ('Costumes', 'Complete costume collections for all occasions', 'assets/images/costumes-banner.jpg'),
    ('Decor', 'Event decorations and backdrops', 'assets/images/decor-banner.jpg')
ON DUPLICATE KEY UPDATE name = name;

-- Insert subcategories for Costumes
INSERT INTO subcategories (name, image_url, category_id) 
VALUES 
    ('Men', 'assets/images/men-costumes.jpg', (SELECT id FROM categories WHERE name = 'Costumes')),
    ('Women', 'assets/images/women-costumes.jpg', (SELECT id FROM categories WHERE name = 'Costumes')),
    ('Kids', 'assets/images/kids-costumes.jpg', (SELECT id FROM categories WHERE name = 'Costumes'))
ON DUPLICATE KEY UPDATE name = name;

-- Insert subcategories for Decor
INSERT INTO subcategories (name, image_url, category_id) 
VALUES 
    ('Backdrop', 'assets/images/backdrop-decor.jpg', (SELECT id FROM categories WHERE name = 'Decor')),
    ('Leg', 'assets/images/leg-decor.jpg', (SELECT id FROM categories WHERE name = 'Decor'))
ON DUPLICATE KEY UPDATE name = name;

-- Insert sample products for Men's Costumes
INSERT INTO products (name, description, sale_price, rent_price, availability, image_cover, rating, stock_quantity, category_id, subcategory_id)
VALUES 
    ('Classic British Gentleman Suit', 'Victorian era gentleman costume with top hat', 89.99, 29.99, 'BOTH', 'assets/images/products/gentleman-suit.jpg', 4.8, 15, 
        (SELECT id FROM categories WHERE name = 'Costumes'), 
        (SELECT id FROM subcategories WHERE name = 'Men')),
    ('Royal Guard Uniform', 'Traditional British Royal Guard costume', 79.99, 25.99, 'BOTH', 'assets/images/products/royal-guard.jpg', 4.9, 10, 
        (SELECT id FROM categories WHERE name = 'Costumes'), 
        (SELECT id FROM subcategories WHERE name = 'Men')),
    ('Medieval Knight Armor', 'Full medieval knight costume with armor', 119.99, 39.99, 'BOTH', 'assets/images/products/knight-armor.jpg', 4.7, 8, 
        (SELECT id FROM categories WHERE name = 'Costumes'), 
        (SELECT id FROM subcategories WHERE name = 'Men'))
ON DUPLICATE KEY UPDATE name = name;

-- Insert sample products for Women's Costumes
INSERT INTO products (name, description, sale_price, rent_price, availability, image_cover, rating, stock_quantity, category_id, subcategory_id)
VALUES 
    ('Victorian Era Dress', 'Elegant Victorian style gown', 99.99, 34.99, 'BOTH', 'assets/images/products/victorian-dress.jpg', 4.9, 12, 
        (SELECT id FROM categories WHERE name = 'Costumes'), 
        (SELECT id FROM subcategories WHERE name = 'Women')),
    ('Queen Elizabeth Costume', 'Royal queen costume with crown', 129.99, 44.99, 'BOTH', 'assets/images/products/queen-costume.jpg', 5.0, 6, 
        (SELECT id FROM categories WHERE name = 'Costumes'), 
        (SELECT id FROM subcategories WHERE name = 'Women')),
    ('Medieval Princess Gown', 'Beautiful princess costume', 89.99, 29.99, 'BOTH', 'assets/images/products/princess-gown.jpg', 4.8, 10, 
        (SELECT id FROM categories WHERE name = 'Costumes'), 
        (SELECT id FROM subcategories WHERE name = 'Women'))
ON DUPLICATE KEY UPDATE name = name;

-- Insert sample products for Kids' Costumes
INSERT INTO products (name, description, sale_price, rent_price, availability, image_cover, rating, stock_quantity, category_id, subcategory_id)
VALUES 
    ('Little Prince Costume', 'Adorable prince costume for kids', 49.99, 19.99, 'BOTH', 'assets/images/products/little-prince.jpg', 4.9, 20, 
        (SELECT id FROM categories WHERE name = 'Costumes'), 
        (SELECT id FROM subcategories WHERE name = 'Kids')),
    ('Princess Dress', 'Fairy tale princess dress for girls', 54.99, 22.99, 'BOTH', 'assets/images/products/kids-princess.jpg', 5.0, 18, 
        (SELECT id FROM categories WHERE name = 'Costumes'), 
        (SELECT id FROM subcategories WHERE name = 'Kids')),
    ('Knight Costume', 'Brave knight costume for boys', 44.99, 17.99, 'BOTH', 'assets/images/products/kids-knight.jpg', 4.7, 15, 
        (SELECT id FROM categories WHERE name = 'Costumes'), 
        (SELECT id FROM subcategories WHERE name = 'Kids'))
ON DUPLICATE KEY UPDATE name = name;

-- Insert sample products for Backdrop Decor
INSERT INTO products (name, description, sale_price, rent_price, availability, image_cover, rating, stock_quantity, category_id, subcategory_id)
VALUES 
    ('Castle Wall Backdrop', 'Medieval castle wall backdrop 10x8ft', 149.99, 49.99, 'BOTH', 'assets/images/products/castle-backdrop.jpg', 4.8, 5, 
        (SELECT id FROM categories WHERE name = 'Decor'), 
        (SELECT id FROM subcategories WHERE name = 'Backdrop')),
    ('Royal Palace Backdrop', 'Elegant palace interior backdrop', 169.99, 54.99, 'BOTH', 'assets/images/products/palace-backdrop.jpg', 4.9, 4, 
        (SELECT id FROM categories WHERE name = 'Decor'), 
        (SELECT id FROM subcategories WHERE name = 'Backdrop'))
ON DUPLICATE KEY UPDATE name = name;

-- Insert sample products for Leg Decor
INSERT INTO products (name, description, sale_price, rent_price, availability, image_cover, rating, stock_quantity, category_id, subcategory_id)
VALUES 
    ('British Flag Bunting', 'Union Jack bunting decoration 20ft', 34.99, 14.99, 'BOTH', 'assets/images/products/bunting.jpg', 4.7, 30, 
        (SELECT id FROM categories WHERE name = 'Decor'), 
        (SELECT id FROM subcategories WHERE name = 'Leg')),
    ('Royal Crown Centerpiece', 'Gold crown table centerpiece set', 24.99, 9.99, 'BOTH', 'assets/images/products/crown-centerpiece.jpg', 4.8, 25, 
        (SELECT id FROM categories WHERE name = 'Decor'), 
        (SELECT id FROM subcategories WHERE name = 'Leg'))
ON DUPLICATE KEY UPDATE name = name;

-- Insert partners
INSERT INTO partners (title, category, description, icon, since, rating, gradient)
VALUES 
    ('Premium Event Planning', 'Event Services', 'Full-service event planning and coordination', 'fas fa-calendar-alt', 2015, 4.9, 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)'),
    ('Professional Photography', 'Photography', 'Capturing your special moments', 'fas fa-camera', 2016, 4.8, 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)'),
    ('Catering Excellence', 'Food & Beverage', 'Gourmet catering for all occasions', 'fas fa-utensils', 2017, 4.9, 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)'),
    ('Sound & Lighting', 'Technical Services', 'Professional AV equipment and setup', 'fas fa-music', 2018, 4.7, 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)'),
    ('Floral Arrangements', 'Decoration', 'Beautiful floral designs', 'fas fa-leaf', 2019, 4.8, 'linear-gradient(135deg, #fa709a 0%, #fee140 100%)'),
    ('Entertainment Services', 'Entertainment', 'Live performers and DJs', 'fas fa-star', 2016, 4.9, 'linear-gradient(135deg, #30cfd0 0%, #330867 100%)'),
    ('Venue Decoration', 'Decoration', 'Complete venue transformation', 'fas fa-paint-brush', 2015, 4.8, 'linear-gradient(135deg, #a8edea 0%, #fed6e3 100%)'),
    ('Transportation Services', 'Logistics', 'Luxury transportation solutions', 'fas fa-car', 2017, 4.7, 'linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%)')
ON DUPLICATE KEY UPDATE title = title;
```

---

## ⚠️ Important Note: BCrypt Password Hash

The admin password hash in the SQL script above is a **placeholder**. You need to generate a proper BCrypt hash for "admin123".

### Generate the correct hash:

1. **Using Online Tool:**
   - Go to: https://bcrypt-generator.com/
   - Enter: `admin123`
   - Rounds: `10`
   - Copy the generated hash

2. **Using Java (recommended):**
   ```java
   import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
   
   public class PasswordHashGenerator {
       public static void main(String[] args) {
           BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
           String rawPassword = "admin123";
           String hashedPassword = encoder.encode(rawPassword);
           System.out.println(hashedPassword);
       }
   }
   ```

3. **Replace the password in data.sql** with the generated hash:
   ```sql
   INSERT INTO users (username, email, password, role) 
   VALUES ('admin', 'admin@DreamScene.com', 'YOUR_GENERATED_HASH_HERE', 'ADMIN');
   ```

---

## 🚀 Final Steps

### 1. Build the Project
```bash
cd d:\Angular Projects\Portfolio\DreamScene-backend
mvn clean install
```

### 2. Run the Application
```bash
mvn spring-boot:run
```

### 3. Test the API

#### Login:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
```

#### Get Categories (No auth required):
```bash
curl http://localhost:8080/api/categories
```

#### Create Product (Admin only - requires token):
```bash
curl -X POST http://localhost:8080/api/admin/products \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{...product data...}"
```

---

## ✅ Complete Checklist

- [ ] Create all Service classes (6 files)
- [ ] Create all Controller classes (5 files)
- [ ] Create data.sql with proper BCrypt hash
- [ ] Run `mvn clean install`
- [ ] Start MySQL server
- [ ] Create database: `CREATE DATABASE DreamScene_db;`
- [ ] Run application: `mvn spring-boot:run`
- [ ] Test login endpoint
- [ ] Test public endpoints (categories, products, partners)
- [ ] Test admin endpoints with JWT token
- [ ] Verify database tables and data

---

## 🎯 Next: Angular Integration

Once the backend is working, integrate with Angular:

1. Update `environment.ts`:
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

2. Create `backend.service.ts` to call Spring Boot APIs
3. Update existing components to use the new service
4. Store JWT token in localStorage after login
5. Add HTTP interceptor to inject token in requests

---

**🎉 Your complete Spring Boot backend is now ready!**
