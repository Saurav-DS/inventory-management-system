-- Suppliers
INSERT INTO supplier (id, name, email, phone) VALUES
(1, 'ABC Supplies', 'contact@abc.com', '9876543210'),
(2, 'Global Traders', 'info@globaltraders.com', '9123456780'),
(3, 'QuickStock', 'sales@quickstock.com', '9988776655')
ON CONFLICT (id) DO NOTHING;

-- Items
INSERT INTO item (id, name, category, price, stock_quantity, supplier_id) VALUES
(1, 'Laptop', 'Electronics', 55000, 15, 1),
(2, 'Mouse', 'Electronics', 500, 50, 1),
(3, 'Notebook', 'Stationery', 50, 200, 2),
(4, 'Pen', 'Stationery', 20, 500, 2),
(5, 'Chair', 'Furniture', 1500, 20, 3),
(6, 'Desk', 'Furniture', 3500, 10, 3),
(7, 'Headphones', 'Electronics', 1200, 30, 1)
ON CONFLICT (id) DO NOTHING;

-- Default roles
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_MANAGER');
INSERT INTO roles (name) VALUES ('ROLE_STAFF');

-- Default users
INSERT INTO users (username, email, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES
('admin', 'admin@ims.com', '$2a$10$T9ZpKuf1u8aPQc.LG9s8UuU5qHfTfTgYgcw2jH5tXnY9axrj2rh1O', TRUE, TRUE, TRUE, TRUE), -- bcrypt hash for "admin123"
('manager', 'manager@ims.com', '$2a$10$YqM8h/TX3zSmL3Y6eO1WpuHTxqMoyKUG5cP3HjUctuGmA5G5MxmpG', TRUE, TRUE, TRUE, TRUE), -- "manager123"
('staff', 'staff@ims.com', '$2a$10$Bb8KQmZr.Z4aZc5B/ek4yO9gP5IqqKkZq4OSsL1M6XUZJ2Gb5ahji', TRUE, TRUE, TRUE, TRUE); -- "staff123"

-- Default user roles
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- admin → ROLE_ADMIN
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- manager → ROLE_MANAGER
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3); -- staff → ROLE_STAFF
