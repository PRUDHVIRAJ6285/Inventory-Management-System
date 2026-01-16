-- Create database
CREATE DATABASE IF NOT EXISTS inventory_db;
USE inventory_db;

-- -------------------------
-- Users table (login)
-- -------------------------
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- -------------------------
-- Products table
-- -------------------------
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    sku VARCHAR(50) NOT NULL UNIQUE,
    category VARCHAR(50),
    price DECIMAL(10,2),
    quantity INT
);

-- -------------------------
-- Alerts table
-- -------------------------
CREATE TABLE alerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    message VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- -------------------------
-- Default admin user
-- -------------------------
INSERT INTO users (username, password)
VALUES ('admin', 'admin123');

-- -------------------------
-- Sample products (optional but useful)
-- -------------------------
INSERT INTO products (name, sku, category, price, quantity) VALUES
('Laptop', 'ELE001', 'Electronics', 55000, 10),
('Mouse', 'ACC002', 'Accessories', 500, 3),
('Keyboard', 'ACC003', 'Accessories', 1200, 7),
('Chair', 'FUR004', 'Furniture', 3000, 2);
