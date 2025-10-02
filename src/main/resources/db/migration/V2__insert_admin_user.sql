INSERT INTO users (id, email, password, role, created_at, updated_at)
VALUES
(
    '1',
    'admin@cinema.com',
    '$2b$12$d01bgPIY6/RKiffyrRvxOOtbVzYzm31E3pErW.RU4RX8zrrdeno6m', -- bcrypt "Admin123!"
    'ADMIN',
    datetime('now'),
    datetime('now')
),
(
    '2',
    'user@cinema.com',
    '$2b$12$an17DioCP2RuJAadAaR8B.YaPPg20HFaWAJV83rQXBeJNeVTmEd/i', -- bcrypt "User123!"
    'USER',
    datetime('now'),
    datetime('now')
);
