CREATE TABLE IF NOT EXISTS users (
    id bigserial primary key,
    username varchar(255) unique
);

INSERT INTO users VALUES
(1, 'Peter Ermakov'),
(2, 'Anna Sokolova'),
(3, 'Lada The Cat');