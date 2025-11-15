-- ============================================
-- DreamScene Database Initialization Script
-- ============================================
-- This script will run automatically after Hibernate creates the tables
-- Make sure to replace the password hash with a real BCrypt hash!

-- Insert admin user (password: admin123)
-- TODO: Replace with actual BCrypt hash generated from BCryptPasswordEncoder
INSERT INTO users (id, username, email, password, role, created_at) 
VALUES (1, 'admin', 'admin@dreamscene.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', NOW())
ON DUPLICATE KEY UPDATE username = username;

-- Insert categories
INSERT INTO categories (id, name, description, image_url, created_at) 
VALUES 
    (1, 'Costumes', 'Complete costume collections for all occasions', 'assets/images/costumes-banner.jpg', NOW()),
    (2, 'Decor', 'Event decorations and backdrops', 'assets/images/decor-banner.jpg', NOW())
ON DUPLICATE KEY UPDATE name = name;

-- Insert subcategories for Costumes
INSERT INTO subcategories (id, name, image_url, category_id, created_at) 
VALUES 
    (1, 'Men', 'assets/images/men-costumes.jpg', 1, NOW()),
    (2, 'Women', 'assets/images/women-costumes.jpg', 1, NOW()),
    (3, 'Kids', 'assets/images/kids-costumes.jpg', 1, NOW())
ON DUPLICATE KEY UPDATE name = name;

-- Insert subcategories for Decor
INSERT INTO subcategories (id, name, image_url, category_id, created_at) 
VALUES 
    (4, 'Backdrop', 'assets/images/backdrop-decor.jpg', 2, NOW()),
    (5, 'Leg', 'assets/images/leg-decor.jpg', 2, NOW())
ON DUPLICATE KEY UPDATE name = name;

-- Insert sample products for Men's Costumes
INSERT INTO products (id, name, description, sale_price, rent_price, availability, image_cover, rating, stock_quantity, category_id, subcategory_id, created_at)
VALUES 
    (1, 'Classic British Gentleman Suit', 'Victorian era gentleman costume with top hat and cane. Perfect for themed parties and events.', 89.99, 29.99, 'BOTH', 'assets/images/products/gentleman-suit.jpg', 4.8, 15, 1, 1, NOW()),
    (2, 'Royal Guard Uniform', 'Traditional British Royal Guard costume with authentic details and bearskin hat.', 79.99, 25.99, 'BOTH', 'assets/images/products/royal-guard.jpg', 4.9, 10, 1, 1, NOW()),
    (3, 'Medieval Knight Armor', 'Full medieval knight costume with armor, helmet, and sword. Impressive and detailed.', 119.99, 39.99, 'BOTH', 'assets/images/products/knight-armor.jpg', 4.7, 8, 1, 1, NOW())
ON DUPLICATE KEY UPDATE name = name;

-- Insert sample products for Women's Costumes
INSERT INTO products (id, name, description, sale_price, rent_price, availability, image_cover, rating, stock_quantity, category_id, subcategory_id, created_at)
VALUES 
    (4, 'Victorian Era Dress', 'Elegant Victorian style gown with corset and petticoat. Perfect for period events.', 99.99, 34.99, 'BOTH', 'assets/images/products/victorian-dress.jpg', 4.9, 12, 1, 2, NOW()),
    (5, 'Queen Elizabeth Costume', 'Royal queen costume with crown, scepter, and regal gown. Feel like royalty!', 129.99, 44.99, 'BOTH', 'assets/images/products/queen-costume.jpg', 5.0, 6, 1, 2, NOW()),
    (6, 'Medieval Princess Gown', 'Beautiful princess costume with flowing gown and tiara. Fairy tale perfection.', 89.99, 29.99, 'BOTH', 'assets/images/products/princess-gown.jpg', 4.8, 10, 1, 2, NOW())
ON DUPLICATE KEY UPDATE name = name;

-- Insert sample products for Kids' Costumes
INSERT INTO products (id, name, description, sale_price, rent_price, availability, image_cover, rating, stock_quantity, category_id, subcategory_id, created_at)
VALUES 
    (7, 'Little Prince Costume', 'Adorable prince costume for kids with crown and cape. Perfect for little royals.', 49.99, 19.99, 'BOTH', 'assets/images/products/little-prince.jpg', 4.9, 20, 1, 3, NOW()),
    (8, 'Princess Dress', 'Fairy tale princess dress for girls with sparkles and tulle. Dreams come true!', 54.99, 22.99, 'BOTH', 'assets/images/products/kids-princess.jpg', 5.0, 18, 1, 3, NOW()),
    (9, 'Knight Costume', 'Brave knight costume for boys with shield and sword. Ready for adventure!', 44.99, 17.99, 'BOTH', 'assets/images/products/kids-knight.jpg', 4.7, 15, 1, 3, NOW())
ON DUPLICATE KEY UPDATE name = name;

-- Insert sample products for Backdrop Decor
INSERT INTO products (id, name, description, sale_price, rent_price, availability, image_cover, rating, stock_quantity, category_id, subcategory_id, created_at)
VALUES 
    (10, 'Castle Wall Backdrop', 'Medieval castle wall backdrop 10x8ft. Perfect for photos and event decoration.', 149.99, 49.99, 'BOTH', 'assets/images/products/castle-backdrop.jpg', 4.8, 5, 2, 4, NOW()),
    (11, 'Royal Palace Backdrop', 'Elegant palace interior backdrop with throne room design. Create a royal atmosphere.', 169.99, 54.99, 'BOTH', 'assets/images/products/palace-backdrop.jpg', 4.9, 4, 2, 4, NOW())
ON DUPLICATE KEY UPDATE name = name;

-- Insert sample products for Leg Decor
INSERT INTO products (id, name, description, sale_price, rent_price, availability, image_cover, rating, stock_quantity, category_id, subcategory_id, created_at)
VALUES 
    (12, 'British Flag Bunting', 'Union Jack bunting decoration 20ft. Perfect for British-themed events.', 34.99, 14.99, 'BOTH', 'assets/images/products/bunting.jpg', 4.7, 30, 2, 5, NOW()),
    (13, 'Royal Crown Centerpiece', 'Gold crown table centerpiece set of 6. Add royal elegance to your tables.', 24.99, 9.99, 'BOTH', 'assets/images/products/crown-centerpiece.jpg', 4.8, 25, 2, 5, NOW())
ON DUPLICATE KEY UPDATE name = name;

-- Insert partners
INSERT INTO partners (id, title, category, description, icon, since, rating, gradient, created_at)
VALUES 
    (1, 'Premium Event Planning', 'Event Services', 'Full-service event planning and coordination for all occasions', 'fas fa-calendar-alt', 2015, 4.9, 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)', NOW()),
    (2, 'Professional Photography', 'Photography', 'Capturing your special moments with artistic excellence', 'fas fa-camera', 2016, 4.8, 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)', NOW()),
    (3, 'Catering Excellence', 'Food & Beverage', 'Gourmet catering for all occasions with international cuisine', 'fas fa-utensils', 2017, 4.9, 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)', NOW()),
    (4, 'Sound & Lighting', 'Technical Services', 'Professional AV equipment and technical setup for events', 'fas fa-music', 2018, 4.7, 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)', NOW()),
    (5, 'Floral Arrangements', 'Decoration', 'Beautiful floral designs and arrangements for any occasion', 'fas fa-leaf', 2019, 4.8, 'linear-gradient(135deg, #fa709a 0%, #fee140 100%)', NOW()),
    (6, 'Entertainment Services', 'Entertainment', 'Live performers, DJs, and entertainment for memorable events', 'fas fa-star', 2016, 4.9, 'linear-gradient(135deg, #30cfd0 0%, #330867 100%)', NOW()),
    (7, 'Venue Decoration', 'Decoration', 'Complete venue transformation and themed decoration services', 'fas fa-paint-brush', 2015, 4.8, 'linear-gradient(135deg, #a8edea 0%, #fed6e3 100%)', NOW()),
    (8, 'Transportation Services', 'Logistics', 'Luxury transportation solutions for guests and events', 'fas fa-car', 2017, 4.7, 'linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%)', NOW())
ON DUPLICATE KEY UPDATE title = title;

-- ============================================
-- NOTES:
-- ============================================
-- 1. The admin password hash is for "admin123" (BCrypt rounds: 10)
-- 2. All image URLs use "assets/images/" prefix - you'll need actual images
-- 3. IDs are explicitly set to prevent conflicts on re-runs
-- 4. ON DUPLICATE KEY UPDATE prevents errors if script runs multiple times
-- 5. Tables are auto-created by Hibernate from Entity classes
-- ============================================
