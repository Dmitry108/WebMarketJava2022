INSERT INTO users (username, password, email) VALUES
    ('Bob', '$2a$12$uyCRPqtYm5ocpxzYqUYiMOvxhzJCFcMIWxjbuXCyUr4hSy8Ype.qu', 'bob@mail.ru'),
    ('Admin', '$2a$12$uyCRPqtYm5ocpxzYqUYiMOvxhzJCFcMIWxjbuXCyUr4hSy8Ype.qu', 'admin@mail.ru');

INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1), (2, 1), (2, 2);