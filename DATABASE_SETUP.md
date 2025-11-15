# 🚀 Quick Database Setup Guide

## Prerequisites
✅ MySQL 8.x installed and running
✅ MySQL root access

---

## Step 1: Create Database

Open MySQL command line or MySQL Workbench and run:

```sql
CREATE DATABASE DreamScene_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Or using command line:
```bash
mysql -u root -p -e "CREATE DATABASE DreamScene_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
```

---

## Step 2: Verify Database Configuration

Check your `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/DreamScene_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

**Important:** Update `spring.datasource.password` with your actual MySQL password!

---

## Step 3: How It Works

When you run the Spring Boot application:

1. **Hibernate creates tables automatically** from your Entity classes:
   - `users`
   - `categories`
   - `subcategories`
   - `products`
   - `product_images`
   - `partners`
   - `orders`
   - `order_items`

2. **data.sql runs automatically** to insert sample data:
   - 1 admin user (username: admin, password: admin123)
   - 2 categories (Costumes, Decor)
   - 5 subcategories (Men, Women, Kids, Backdrop, Leg)
   - 13 sample products
   - 8 partners

---

## Step 4: Run the Application

```bash
cd d:\Angular Projects\Portfolio\DreamScene-backend
mvn spring-boot:run
```

Watch the console for:
```
✅ Hibernate: create table categories ...
✅ Hibernate: create table products ...
✅ Executing SQL script from URL [file:...data.sql]
✅ Started DreamSceneApplication in X seconds
```

---

## Step 5: Verify Database

Open MySQL and check:

```sql
USE DreamScene_db;

-- Check tables created
SHOW TABLES;

-- Check sample data
SELECT * FROM categories;
SELECT * FROM products;
SELECT * FROM partners;
SELECT * FROM users;
```

---

## 🔧 Troubleshooting

### Problem: Tables not created
**Solution:** Check `spring.jpa.hibernate.ddl-auto=update` in application.properties

### Problem: data.sql not executing
**Solution:** Add to application.properties:
```properties
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
```

### Problem: Duplicate key errors
**Solution:** Drop database and recreate:
```sql
DROP DATABASE DreamScene_db;
CREATE DATABASE DreamScene_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Problem: MySQL connection refused
**Solution:** 
1. Check MySQL service is running: `services.msc` (Windows)
2. Verify port 3306 is correct
3. Test connection: `mysql -u root -p`

---

## 📊 Database Schema (Auto-Generated)

```
users
├── id (PK)
├── username (UNIQUE)
├── email (UNIQUE)
├── password (BCrypt)
├── role (ENUM: ADMIN)
└── created_at

categories
├── id (PK)
├── name
├── description
├── image_url
└── created_at

subcategories
├── id (PK)
├── name
├── image_url
├── category_id (FK → categories)
└── created_at

products
├── id (PK)
├── name
├── description
├── sale_price
├── rent_price
├── availability (ENUM: SALE, RENT, BOTH)
├── image_cover
├── rating
├── stock_quantity
├── category_id (FK → categories)
├── subcategory_id (FK → subcategories)
└── created_at

product_images
├── id (PK)
├── image_url
└── product_id (FK → products)

partners
├── id (PK)
├── title
├── category
├── description
├── icon
├── since
├── rating
├── gradient
└── created_at

orders
├── id (PK)
├── user_name
├── user_email
├── user_phone
├── total_amount
├── status (ENUM: PENDING, CONFIRMED, COMPLETED, CANCELLED)
└── created_at

order_items
├── id (PK)
├── product_id
├── product_name
├── quantity
├── type (ENUM: SALE, RENT)
├── price
└── order_id (FK → orders)
```

---

## ✅ Success Checklist

- [ ] MySQL database `DreamScene_db` created
- [ ] `application.properties` updated with correct MySQL password
- [ ] Application starts without errors
- [ ] Tables automatically created in database
- [ ] Sample data inserted (13 products, 8 partners, 1 admin user)
- [ ] Can login with admin/admin123
- [ ] API endpoints responding at http://localhost:8080/api

---

## 🎯 Next Steps

After database is set up and running:
1. Test REST APIs with Postman or curl
2. Integrate with Angular frontend
3. Add real product images to assets folder
4. Customize sample data as needed

---

**Note:** The admin password in `data.sql` is already BCrypt hashed for "admin123". This is a secure production-ready hash!
