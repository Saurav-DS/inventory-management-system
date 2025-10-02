-- Drop tables if they exist (We dont want to persist data)
DROP TABLE IF EXISTS item CASCADE;
DROP TABLE IF EXISTS supplier CASCADE;

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