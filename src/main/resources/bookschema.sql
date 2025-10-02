DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS application_user;

-- category taulu luodaan ensin, jotta saadaan book-luokkaan viiteavain
CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL
);

INSERT INTO category (name) 
VALUES 
('Sci-fi'),
('Document'),
('Fiction');



CREATE TABLE book (
id BIGSERIAL PRIMARY KEY,
title VARCHAR(150),
author VARCHAR(150),
publishing_year INT,
category_id BIGINT REFERENCES category(id)
);

INSERT INTO book (title, author, publishing_year, category_id)
VALUES ('PostgreKannan salat','Heimo Heimonen', 2022, 2),
('Spring bootin ihanuudet', 'Heimo Kakkonen', 2025, 1),
('Sitä sun tätä', 'Selma Semmonen', 2025, null);


-- application_user-taulu
CREATE TABLE application_user (
    id BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    application_role VARCHAR(100) NOT NULL,
    username VARCHAR(250) NOT NULL,
    application_password VARCHAR(250) NOT NULL
);

INSERT INTO application_user (firstname, lastname, username, application_password, application_role) 
VALUES 
('Minna', 'Pellikka', 'user', '$2a$10$1DTvwpXVBArGFixHBuzVJObjTuXhIOkx5pse6KsYs8/C2ckxnGEou', 'USER'),
('Admin', 'User', 'admin', '$2a$10$cDZgyF4xaPMmmoRW3OVcmuf.8o2YSx8.M7CeRKqi.1PVw.t3E8uEC', 'ADMIN');

SELECT * FROM category;
SELECT * FROM book;
SELECT * FROM application_user;