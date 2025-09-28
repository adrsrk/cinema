CREATE TABLE users (
                       id INTEGER PRIMARY KEY,
                       email TEXT NOT NULL UNIQUE,
                       password TEXT NOT NULL,
                       role TEXT NOT NULL,
                       created_at INTEGER NOT NULL ,
                       updated_at INTEGER
);

CREATE TABLE movie (
                       id INTEGER PRIMARY KEY,
                       title TEXT,
                       description TEXT,
                       duration INTEGER,
                       poster_url TEXT,
                       created_at INTEGER NOT NULL ,
                       updated_at INTEGER
);

CREATE TABLE hall (
                      id INTEGER PRIMARY KEY,
                      name TEXT,
                      rows INTEGER,
                      seats_per_row INTEGER,
                      created_at INTEGER NOT NULL ,
                      updated_at INTEGER
);

CREATE TABLE session (
                     id INTEGER PRIMARY KEY,
                     movie_id INTEGER,
                     hall_id INTEGER,
                     start_time INTEGER NOT NULL ,
                     price NUMERIC,
                     created_at INTEGER NOT NULL ,
                     updated_at INTEGER
);

CREATE TABLE refresh_token (
                   id INTEGER PRIMARY KEY,
                   token TEXT NOT NULL UNIQUE,
                   expires_at INTEGER,
                   user_id INTEGER,
                   created_at INTEGER NOT NULL ,
                   updated_at INTEGER
);
