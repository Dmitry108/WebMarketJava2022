SET search_path TO market;

DROP TABLE IF EXISTS orders CASCADE;
CREATE TABLE IF NOT EXISTS orders (
    id          BIGSERIAL PRIMARY KEY,
--    user_id     BIGINT NOT NULL REFERENCES users (id),
    username VARCHAR(255) NOT NULL,
    total_price NUMERIC(8, 2) NOT NULL,
    address     VARCHAR(255),
    phone       VARCHAR(255),
    created_at  TIMESTAMP DEFAULT current_timestamp,
    updated_at  TIMESTAMP DEFAULT current_timestamp
);

DROP TABLE IF EXISTS order_items CASCADE;
CREATE TABLE IF NOT EXISTS order_items (
    id                  BIGSERIAL PRIMARY KEY,
    order_id            BIGINT NOT NULL REFERENCES orders (id),
    product_id          BIGINT NOT NULL REFERENCES products (id),
    quantity            INT NOT NULL,
    price_per_product   NUMERIC(8, 2) NOT NULL,
    price               NUMERIC(8, 2) NOT NULL,
    created_at  TIMESTAMP DEFAULT current_timestamp,
    updated_at  TIMESTAMP DEFAULT current_timestamp
);