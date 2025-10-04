-- Drop tables if they exist (We dont want to persist data)
DROP TABLE IF EXISTS item CASCADE;
DROP TABLE IF EXISTS supplier CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE supplier (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(50)
);

CREATE TABLE item (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255),
    price DOUBLE PRECISION NOT NULL,
    stock_quantity INTEGER NOT NULL,
    supplier_id BIGINT,
    CONSTRAINT fk_supplier
        FOREIGN KEY(supplier_id) REFERENCES supplier(id)
        ON DELETE SET NULL
);

-- Indexes for faster searches
CREATE INDEX idx_item_name ON item(name);
CREATE INDEX idx_item_category ON item(category);
CREATE INDEX idx_item_supplier ON item(supplier_id);


-- Security
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- User-Role MANY-TO-MANY RELATION
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
