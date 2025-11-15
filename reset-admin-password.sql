-- Reset Admin Password Script
-- Run this in MySQL Workbench or command line
-- This will set the admin password to: admin123

USE dreamscene_db;

-- Delete existing admin if exists
DELETE FROM users WHERE username = 'admin';

-- Insert admin user with password: admin123
INSERT INTO users (username, email, password, role, created_at) 
VALUES ('admin', 'admin@dreamscene.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', NOW());

-- Verify the user was created
SELECT id, username, email, role FROM users WHERE username = 'admin';
