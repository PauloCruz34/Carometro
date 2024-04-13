CREATE SCHEMA CarometroDb;
USE CarometroDB;

CREATE TABLE funcionários(
	re INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    foto LONGBLOB NOT NULL
);

DESCRIBE funcionários;
    