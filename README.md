# DreamScene Backend API

Spring Boot REST API for DreamScene E-commerce Application

## 📋 Project Overview

This backend provides RESTful APIs for managing costumes, decor products, partners, and orders for the DreamScene e-commerce platform.

## 🛠️ Technology Stack

- **Spring Boot** 3.2.0
- **Spring Security** with JWT Authentication
- **Spring Data JPA** for database operations
- **MySQL** 8.x database
- **Maven** for dependency management
- **Lombok** for reducing boilerplate code

## 📦 Project Structure

```
DreamScene-backend/
├── src/
│   ├── main/
│   │   ├── java/com/DreamScene/
│   │   │   ├── DreamSceneApplication.java
│   │   │   ├── config/
│   │   │   │   ├── CorsConfig.java
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── JwtAuthenticationFilter.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── CategoryController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── PartnerController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   └── AdminController.java
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   ├── CategoryRequest.java
│   │   │   │   │   ├── SubcategoryRequest.java
│   │   │   │   │   ├── ProductRequest.java
│   │   │   │   │   ├── PartnerRequest.java
│   │   │   │   │   └── OrderRequest.java
│   │   │   │   └── response/
│   │   │   │       ├── LoginResponse.java
│   │   │   │       ├── ProductResponse.java
│   │   │   │       ├── CategoryResponse.java
│   │   │   │       └── ApiResponse.java
│   │   │   ├── entity/
│   │   │   │   ├── Category.java ✅
│   │   │   │   ├── Subcategory.java ✅
│   │   │   │   ├── Product.java ✅
│   │   │   │   ├── ProductImage.java ✅
│   │   │   │   ├── Partner.java ✅
│   │   │   │   ├── User.java ✅
│   │   │   │   ├── Order.java ✅
│   │   │   │   └── OrderItem.java ✅
│   │   │   ├── repository/
│   │   │   │   ├── CategoryRepository.java ✅
│   │   │   │   ├── SubcategoryRepository.java ✅
│   │   │   │   ├── ProductRepository.java ✅
│   │   │   │   ├── PartnerRepository.java ✅
│   │   │   │   ├── UserRepository.java ✅
│   │   │   │   └── OrderRepository.java ✅
│   │   │   ├── service/
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── CategoryService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   ├── PartnerService.java
│   │   │   │   ├── OrderService.java
│   │   │   │   └── JwtService.java
│   │   │   └── util/
│   │   │       └── JwtUtil.java
│   │   └── resources/
│   │       ├── application.properties ✅
│   │       └── data.sql
│   └── test/
│       └── java/com/DreamScene/
├── pom.xml ✅
└── README.md (this file)
```

## 🚀 Setup Instructions

### Prerequisites
- Java 17 or higher
- MySQL 8.x
- Maven 3.6+
- Your favorite IDE (IntelliJ IDEA recommended)

### Database Setup

1. **Install MySQL** (if not already installed)
   
2. **Create Database**
   ```sql
   CREATE DATABASE DreamScene_db;
   ```

3. **Configure Database Connection**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/DreamScene_db?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   ```

### Running the Application

#### Option 1: Using Maven
```bash
cd DreamScene-backend
mvn clean install
mvn spring-boot:run
```

#### Option 2: Using IDE
1. Import project as Maven project
2. Wait for dependencies to download
3. Run `DreamSceneApplication.java`

The server will start on **http://localhost:8080**

### Initial Login Credentials

**Admin User:**
- Username: `admin`
- Password: `admin123`

## 📡 API Endpoints

### Public Endpoints (No Authentication Required)

#### Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}/subcategories` - Get subcategories by category

#### Products
- `GET /api/products` - Get all products
  - Query Params: `categoryId`, `subcategoryId`, `availability`
- `GET /api/products/{id}` - Get product by ID

#### Partners
- `GET /api/partners` - Get all partners

#### Orders
- `POST /api/orders` - Create new order (guest checkout)

### Admin Endpoints (JWT Token Required)

#### Authentication
- `POST /api/auth/login` - Admin login

#### Admin - Categories
- `POST /api/admin/categories` - Create category
- `PUT /api/admin/categories/{id}` - Update category
- `DELETE /api/admin/categories/{id}` - Delete category

#### Admin - Subcategories
- `POST /api/admin/subcategories` - Create subcategory
- `PUT /api/admin/subcategories/{id}` - Update subcategory
- `DELETE /api/admin/subcategories/{id}` - Delete subcategory

#### Admin - Products
- `POST /api/admin/products` - Create product
- `PUT /api/admin/products/{id}` - Update product
- `DELETE /api/admin/products/{id}` - Delete product

#### Admin - Partners
- `POST /api/admin/partners` - Create partner
- `PUT /api/admin/partners/{id}` - Update partner
- `DELETE /api/admin/partners/{id}` - Delete partner

#### Admin - Orders
- `GET /api/admin/orders` - Get all orders
- `PUT /api/admin/orders/{id}/status` - Update order status

## 🔐 Authentication

The API uses JWT (JSON Web Token) for authentication.

### Login Flow:
1. Send POST request to `/api/auth/login` with credentials
2. Receive JWT token in response
3. Include token in subsequent requests:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

## 📝 Sample API Requests

### Login (Admin)
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

### Get All Products
```bash
GET http://localhost:8080/api/products
```

### Get Products by Category
```bash
GET http://localhost:8080/api/products?categoryId=1
```

### Create Order
```bash
POST http://localhost:8080/api/orders
Content-Type: application/json

{
  "userName": "John Doe",
  "userEmail": "john@example.com",
  "userPhone": "+1234567890",
  "items": [
    {
      "productId": 1,
      "quantity": 2,
      "type": "SALE"
    }
  ]
}
```

### Create Product (Admin Only)
```bash
POST http://localhost:8080/api/admin/products
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "name": "Men's Tuxedo",
  "description": "Classic black tuxedo",
  "salePrice": 499.99,
  "rentPrice": 149.99,
  "availability": "BOTH",
  "imageCover": "https://example.com/image.jpg",
  "categoryId": 1,
  "subcategoryId": 1,
  "stockQuantity": 10
}
```

## ⚙️ Configuration

### CORS Configuration
By default, CORS is configured to allow requests from:
- `http://localhost:4200` (Angular development server)

To add more origins, edit `application.properties`:
```properties
cors.allowed-origins=http://localhost:4200,http://example.com
```

### JWT Configuration
- Token expiration: 24 hours (86400000 ms)
- Secret key is configured in `application.properties`

## 📊 Database Schema

The application will automatically create the following tables:
- `categories`
- `subcategories`
- `products`
- `product_images`
- `partners`
- `users`
- `orders`
- `order_items`

## 🐛 Troubleshooting

### Issue: MySQL Connection Error
**Solution:** Check MySQL service is running and credentials in `application.properties` are correct

### Issue: Port 8080 already in use
**Solution:** Change port in `application.properties`:
```properties
server.port=8081
```

### Issue: JWT Token Invalid
**Solution:** Ensure token is included in Authorization header with "Bearer " prefix

## 📦 Building for Production

```bash
mvn clean package
java -jar target/DreamScene-backend-1.0.0.jar
```

## 🔄 Next Steps

1. Complete the remaining service classes and controllers (see STATUS section below)
2. Test all endpoints with Postman
3. Integrate with Angular frontend
4. Add input validation
5. Implement proper error handling
6. Add logging
7. Write unit tests

## 📌 STATUS

✅ **COMPLETED:**
- Project structure
- Maven configuration
- Database configuration
- Entity classes (8/8)
- Repository interfaces (6/6)

🔄 **IN PROGRESS:**
- DTOs and Request/Response classes
- Service layer
- Controllers
- Security configuration
- SQL initialization script

📋 **REMAINING FILES TO CREATE:**

Due to character limits, I've prepared the core structure. You need to create:
1. **JWT Security files** (3 files)
2. **Service classes** (6 files)
3. **Controllers** (6 files)
4. **DTOs** (15 files)
5. **SQL initialization script** (1 file)

**Total: ~31 additional files**

I can provide these files in the next steps if you'd like to continue!

## 📞 Support

For questions or issues, refer to:
- Spring Boot Documentation: https://spring.io/projects/spring-boot
- Spring Security: https://spring.io/projects/spring-security
- JWT: https://jwt.io/

---

**Created for DreamScene E-commerce Project** 🎩
