-- Create special_orders table
CREATE TABLE IF NOT EXISTS special_orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    image_urls TEXT,
    status ENUM('PENDING', 'APPROVED', 'COMPLETED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
    admin_notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index for faster queries
CREATE INDEX idx_special_orders_email ON special_orders(user_email);
CREATE INDEX idx_special_orders_status ON special_orders(status);
CREATE INDEX idx_special_orders_created_at ON special_orders(created_at DESC);
