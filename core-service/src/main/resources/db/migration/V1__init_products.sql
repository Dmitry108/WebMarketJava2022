DROP SCHEMA IF EXISTS market CASCADE;
CREATE SCHEMA IF NOT EXISTS market;

DROP TABLE IF EXISTS market.products CASCADE;
CREATE TABLE IF NOT EXISTS market.products (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) UNIQUE,
    price       INT,
    created_at  TIMESTAMP DEFAULT current_timestamp,
    updated_at  TIMESTAMP DEFAULT current_timestamp
);