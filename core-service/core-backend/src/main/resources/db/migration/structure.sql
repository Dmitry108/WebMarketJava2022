DROP SCHEMA IF EXISTS market CASCADE;
CREATE SCHEMA IF NOT EXISTS market;

SET search_path TO market;

DROP TABLE IF EXISTS products;
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    price INT NOT NULL,
	created_at TIMESTAMP DEFAULT current_timestamp,
	updated_at TIMESTAMP DEFAULT current_timestamp);

DROP TABLE IF EXISTS products_info;
CREATE TABLE IF NOT EXISTS products_info (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES products (id),
	description TEXT,
	photo_preview_url VARCHAR(255),
	created_at TIMESTAMP DEFAULT current_timestamp,
	updated_at TIMESTAMP DEFAULT current_timestamp);

DROP TABLE IF EXISTS photos;
CREATE TABLE IF NOT EXISTS photos (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES products (id),
	photo_url VARCHAR(255) NOT NULL,
	created_at TIMESTAMP DEFAULT current_timestamp,
	updated_at TIMESTAMP DEFAULT current_timestamp);

DROP TABLE IF EXISTS categories;
CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
	created_at TIMESTAMP DEFAULT current_timestamp,
	updated_at TIMESTAMP DEFAULT current_timestamp);

DROP TABLE IF EXISTS products_categories;
CREATE TABLE IF NOT EXISTS products_categories (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES products (id),
	category_id BIGINT NOT NULL REFERENCES categories (id));

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
	name VARCHAR(255),
	surname VARCHAR(255),
	username VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	email VARCHAR(255) UNIQUE,
	created_at TIMESTAMP DEFAULT current_timestamp,
	updated_at TIMESTAMP DEFAULT current_timestamp);

DROP TABLE IF EXISTS roles;
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
	created_at TIMESTAMP DEFAULT current_timestamp,
	updated_at TIMESTAMP DEFAULT current_timestamp);

DROP TABLE IF EXISTS users_roles;
CREATE TABLE IF NOT EXISTS users_roles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users (id),
	role_id BIGINT NOT NULL REFERENCES roles (id),
	created_at TIMESTAMP DEFAULT current_timestamp,
	updated_at TIMESTAMP DEFAULT current_timestamp);

DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users (id),
	product_id BIGINT NOT NULL REFERENCES products (id),
	quantity INT,
	total_price INT,
	created_at TIMESTAMP DEFAULT current_timestamp,
	updated_at TIMESTAMP DEFAULT current_timestamp);

DROP TABLE IF EXISTS user_comments;
CREATE TABLE IF NOT EXISTS user_comments (
    id BIGSERIAL PRIMARY KEY,
	user_id BIGINT NOT NULL REFERENCES users (id),
	product_id BIGINT NOT NULL REFERENCES products (id),
	user_comment TEXT NOT NULL,
	created_at TIMESTAMP DEFAULT current_timestamp,
	updated_at TIMESTAMP DEFAULT current_timestamp);