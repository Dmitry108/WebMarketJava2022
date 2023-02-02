INSERT INTO market.users (username, password) VALUES
    ('Кощей', '$2a$12$jACOjRUGzZhyR75wWzrNYugzV2Ms45Wbxgg9ceSYWLMi3mdkxJwHe'), ('Горыныч', '2'), ('Прострел', '3'),
    ('Полтергейст', '4'), ('Торреадор', '5')
;

INSERT INTO market.roles (name) VALUES
    ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_MANAGER'), ('ROLE_SUPERADMIN')
;

INSERT INTO market.users_roles (user_id, role_id) VALUES
    (1, 1), (1, 2), (1, 3),
    (2, 1),
    (3, 1), (3, 3),
    (4, 1), (4, 2), (4, 3), (4, 4),
    (5, 1)
;