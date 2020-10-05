--ALTER TABLE book DROP 
DROP TABLE book; 
DROP TABLE category; 
DROP TABLE user;

CREATE TABLE category
(id BIGINT NOT NULL AUTO_INCREMENT 
,name VARCHAR(50) NOT NULL
,PRIMARY KEY (id)
);


CREATE TABLE book (
id BIGINT NOT NULL AUTO_INCREMENT
, title VARCHAR(50) NOT NULL
, author VARCHAR(50) NOT NULL
, year INT
, isbn VARCHAR(25)
, price BIGINT
, categoryid BIGINT
, PRIMARY KEY (id)
, FOREIGN KEY (categoryid) REFERENCES category(id));


CREATE TABLE user
(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
,firstName VARCHAR(250) NOT NULL
,lastName VARCHAR(350) NOT NULL
,username VARCHAR(250) NOT NULL
,password VARCHAR(250) NOT NULL
,role VARCHAR(20) NOT NULL);

INSERT INTO category (name) VALUES ('Dekkari'), ('Novelli'), ('Dokumentti');

INSERT INTO book (title, author, year, price, categoryid) 
VALUES ('Kasvoton kuolema', 'Hennig Mankell', 1989, 12, 1), 
('Riian verikoirat','Hennig Mankell', 1990, 13, 1);

INSERT INTO user (firstName, lastName, username, password, role) 
VALUES ("Minna", "Pellikka", "user", "$2a$10$1DTvwpXVBArGFixHBuzVJObjTuXhIOkx5pse6KsYs8/C2ckxnGEou", "USER"), 
("Otto", "Raunonen", "admin", "$2a$10$cDZgyF4xaPMmmoRW3OVcmuf.8o2YSx8.M7CeRKqi.1PVw.t3E8uEC", "ADMIN");

SELECT * FROM book;
SELECT * FROM category;
SELECT * FROM user;

