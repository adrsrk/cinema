-- Фильмы
INSERT INTO movie (id, title, description, duration, poster_url, created_at, updated_at)
VALUES
    ('1','Inception', 'A thief who enters the dreams of others to steal secrets.', 148, 'https://example.com/inception.jpg', 1758362400000, 1758362400000),
    ('2','The Matrix', 'A hacker discovers reality is a simulation.', 136, 'https://example.com/matrix.jpg', 1758448800000, 1758448800000),
    ('3','Interstellar', 'Explorers travel through a wormhole to save humanity.', 169, 'https://example.com/interstellar.jpg', 1758535200000, 1758535200000),
    ('4','Avatar', 'Humans explore and exploit the alien world of Pandora.', 162, 'https://example.com/avatar.jpg', 1758574800000, 1758574800000),
    ('5','The Dark Knight', 'Batman faces the Joker in Gotham City.', 152, 'https://example.com/darkknight.jpg', 1758664800000, 1758664800000),
    ('6','Gladiator', 'A Roman general seeks revenge after being betrayed.', 155, 'https://example.com/gladiator.jpg', 1758668400000, 1758668400000),
    ('7','Titanic', 'A tragic love story aboard the sinking Titanic.', 195, 'https://example.com/titanic.jpg', 1758754800000, 1758754800000),
    ('8','The Shawshank Redemption', 'Two imprisoned men bond over years, finding solace and eventual redemption.', 142, 'https://example.com/shawshank.jpg', 1758758400000, 1758758400000),
    ('9','Forrest Gump', 'The life journey of Forrest Gump, who influences historical events without realizing it.', 144, 'https://example.com/forrestgump.jpg', 1758762000000, 1758762000000),
    ('10','The Godfather', 'The aging patriarch of an organized crime dynasty transfers control to his reluctant son.', 175, 'https://example.com/godfather.jpg', 1758765600000, 1758765600000);

-- Залы
INSERT INTO hall (id, name, rows, seats_per_row, created_at, updated_at)
VALUES
    ('1','Red Hall', 10, 20, 1758358800000, 1758358800000),
    ('2','Blue Hall', 8, 15, 1758447000000, 1758447000000),
    ('3','IMAX Hall', 12, 25, 1758534300000, 1758534300000);

-- Сеансы
INSERT INTO session (id, movie_id, hall_id, start_time, price, created_at, updated_at)
VALUES
    ('1',1, 1, 1758794400000, 2500, 1758642000000, 1758642000000),
    ('2',2, 2, 1758803400000, 2000, 1758645600000, 1758645600000),
    ('3',3, 3, 1758880800000, 3000, 1758649200000, 1758649200000),
    ('4',4, 1, 1758960000000, 2200, 1758652800000, 1758652800000),
    ('5',5, 2, 1758974400000, 2800, 1758656400000, 1758656400000),
    ('6',6, 3, 1759059000000, 2600, 1758660000000, 1758660000000),
    ('7',7, 1, 1759140000000, 2400, 1758663600000, 1758663600000),
    ('8',8, 2, 1759147200000, 2100, 1758667200000, 1758667200000),
    ('9',9, 3, 1759222800000, 2700, 1758670800000, 1758670800000),
    ('10',10, 1, 1759231800000, 2900, 1758674400000, 1758674400000);
