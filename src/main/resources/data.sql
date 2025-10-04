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

-- Insert default admin user (password: admin123)
-- (This will be encoded once Spring Security is ready; for now its plain text)
INSERT INTO users (username, email, password)
VALUES ('admin', 'admin@ims.com', 'admin123');

-- Assign ROLE_ADMIN to admin user
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
