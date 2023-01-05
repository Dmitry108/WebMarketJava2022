DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE IF NOT EXISTS products (id BIGSERIAL, title VARCHAR(255) UNIQUE, price INT, PRIMARY KEY (id));
INSERT INTO products (title, price) VALUES ('Звездолёт', 3000), ('Летающая тарелка', 4200), ('Реактивный самолёт', 2800), ('Воздушный шар', 600), ('НЛО', 6666);

DROP TABLE IF EXISTS customers CASCADE;
CREATE TABLE IF NOT EXISTS customers (id BIGSERIAL, name VARCHAR(255), PRIMARY KEY (id));
INSERT INTO customers (name) VALUES ('Боб'), ('Ядвига'), ('Максимильян');

DROP TABLE IF EXISTS orders CASCADE;
CREATE TABLE IF NOT EXISTS orders (id BIGSERIAL, customer_id BIGINT, product_id BIGINT, PRIMARY KEY (id), FOREIGN KEY (customer_id) REFERENCES (customers.id), FOREIGN KEY (product_id) REFERENCES (products.id));
INSERT INTO orders (customer_id, product_id) VALUES (1, 1), (1, 2), (1, 3), (2, 1), (2, 3), (2, 4), (2, 5), (3, 1), (3, 2), (3, 3), (3, 4), (3, 5);