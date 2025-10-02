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
                     updated_at INTEGER ,
    CONSTRAINT fk_movie_id
                     FOREIGN KEY (movie_id)
                         REFERENCES movie(id) ON DELETE CASCADE ,
    CONSTRAINT fk_hall_id
                     FOREIGN KEY (hall_id)
                         REFERENCES hall(id) ON DELETE CASCADE
);

CREATE TABLE refresh_token (
                   id INTEGER PRIMARY KEY ,
                   token TEXT NOT NULL UNIQUE ,
                   expires_at INTEGER ,
                   user_id INTEGER ,
                   created_at INTEGER NOT NULL ,
                   updated_at INTEGER
);

CREATE TABLE booking (
                    id INTEGER PRIMARY KEY ,
                    user_id INTEGER ,
                    session_id INTEGER ,
                    created_at INTEGER NOT NULL ,
                    updated_at INTEGER ,
                    CONSTRAINT fk_movie_id
                         FOREIGN KEY (user_id)
                            REFERENCES users(id) ON DELETE CASCADE ,
                    CONSTRAINT fk_session_id
                         FOREIGN KEY (session_id)
                            REFERENCES session(id) ON DELETE CASCADE
);

CREATE TABLE booking_seat(
                    id INTEGER PRIMARY KEY ,
                    booking_id INTEGER NOT NULL ,
                    session_id INTEGER NOT NULL ,
                    row_number INTEGER NOT NULL ,
                    seat_number INTEGER NOT NULL ,
                    created_at INTEGER NOT NULL ,
                    updated_at INTEGER ,
                    CONSTRAINT fk_booking_id
                         FOREIGN KEY (booking_id)
                            REFERENCES booking(id) ON DELETE CASCADE ,
                    CONSTRAINT fk_session_id
                         FOREIGN KEY (session_id)
                            REFERENCES session(id) ON DELETE CASCADE ,
                    CONSTRAINT uq_session_seat UNIQUE (session_id, row_number, seat_number)
);
