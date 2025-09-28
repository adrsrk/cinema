INSERT INTO users (email, password, role, created_at, updated_at)
VALUES (
           'admin@cinema.com',
           '$2b$12$d01bgPIY6/RKiffyrRvxOOtbVzYzm31E3pErW.RU4RX8zrrdeno6m', -- bcrypt "Admin123!"
           'ADMIN',
           datetime('now'),
           datetime('now')
       );