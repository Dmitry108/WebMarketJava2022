DROP SCHEMA IF EXISTS market CASCADE;
CREATE SCHEMA IF NOT EXISTS market;

DROP TABLE IF EXISTS market.products;
CREATE TABLE IF NOT EXISTS market.products (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE,
    price INT);