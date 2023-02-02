DROP TABLE IF EXISTS market.users;
CREATE TABLE IF NOT EXISTS market.users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

DROP TABLE IF EXISTS market.roles;
CREATE TABLE IF NOT EXISTS market.roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50)
);

DROP TABLE IF EXISTS market.users_roles;
CREATE TABLE IF NOT EXISTS market.users_roles (
    user_id BIGSERIAL,
    role_id SERIAL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES market.users(id),
    FOREIGN KEY (role_id) REFERENCES market.roles(id)
);