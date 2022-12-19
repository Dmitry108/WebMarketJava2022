DROP TABLE IF EXISTS products;
CREATE TABLE IF NOT EXISTS products (id BIGSERIAL, title VARCHAR(255) UNIQUE, price INT, PRIMARY KEY (id));
INSERT INTO products (title, price) VALUES ('Звездолёт', 3000), ('Летающая тарелка', 4200), ('Реактивный самолёт', 2800), ('Воздушный шар', 600), ('НЛО', 6666);