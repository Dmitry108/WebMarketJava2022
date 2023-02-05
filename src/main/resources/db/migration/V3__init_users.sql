DROP TABLE IF EXISTS market.users;
CREATE TABLE IF NOT EXISTS market.users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS market.roles;
CREATE TABLE IF NOT EXISTS market.roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS market.users_roles;
CREATE TABLE IF NOT EXISTS market.users_roles (
    user_id BIGSERIAL NOT NULL,
    role_id SERIAL NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES market.users (id),
    FOREIGN KEY (role_id) REFERENCES market.roles (id)
);