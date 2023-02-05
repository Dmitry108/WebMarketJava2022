INSERT INTO market.users (username, password) VALUES
    ('Кощей', '$2a$12$O5ER3iI2Kyz/2WZEkoybye/7dhUzNYVAYowwizCInXqTPvSOcGFRa'),
    ('Горыныч', '$2a$12$jp0i03Kb01zBgBA9jMgOj.3dQ/ECj7Jt2GfBFq97xY8T1la8z3O76'),
    ('Прострел', '$2a$12$YpSd/JOoIaVoRSe4xhL.I./Mak4C/HeL5nYCFghD3JwK6NQMH98BC'),
    ('Полтергейст', '$2a$12$hlw5U0jGJdrgRDYQ/1M8p.laqVbAId/JumhHlwJ6tCViiGwy8v5Ru'),
    ('Торреадор', '$2a$12$sGuPAGXaT8eOoQw85gzuaOrTLrJx5.cyzbUDEexuKzoriA.R/OeSC');

INSERT INTO market.roles (name) VALUES
    ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_MANAGER'), ('ROLE_SUPERADMIN');

INSERT INTO market.users_roles (user_id, role_id) VALUES
    (1, 1), (1, 2), (1, 3),
    (2, 1),
    (3, 1), (3, 3),
    (4, 1), (4, 2), (4, 3), (4, 4),
    (5, 1);