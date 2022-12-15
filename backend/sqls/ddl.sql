-- DROP DATABASE IF EXISTS `rental_house`;
-- CREATE DATABASE IF NOT EXISTS `rental_house` CHARACTER SET UTF8MB4 COLLATE UTF8MB4_GENERAL_CI;
-- USE `rental_house`;

CREATE TABLE `rental_house.user` (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone_number VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
    role ENUM('admin','user') NOT NULL DEFAULT 'admin',
    status ENUM('active','deactivated') NOT NULL DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE `rental_house.category` (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

INSERT INTO `rental_house.category` (id, name, created_at) VALUES(1, '1LDK Apartment', CURRENT_TIMESTAMP());
-- INSERT INTO `category` (id, name, created_at) VALUES(2, '2LDK Apartment', CURRENT_TIMESTAMP());
-- INSERT INTO `category` (id, name, created_at) VALUES(3, '3LDK Apartment', CURRENT_TIMESTAMP());
-- INSERT INTO `category` (id, name, created_at) VALUES(4, '1K Apartment', CURRENT_TIMESTAMP());
-- INSERT INTO `category` (id, name, created_at) VALUES(5, '1R Apartment', CURRENT_TIMESTAMP());
-- INSERT INTO `category` (id, name, created_at) VALUES(6, 'Retail shop', CURRENT_TIMESTAMP());
-- INSERT INTO `category` (id, name, created_at) VALUES(7, 'Sharedhouse', CURRENT_TIMESTAMP());
-- INSERT INTO `category` (id, name, created_at) VALUES(8, 'Office', CURRENT_TIMESTAMP());

CREATE TABLE `rental_house.estate` (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(255) NOT NULL,
    rent_fee DOUBLE NOT NULL DEFAULT 0,
    maintenance_fee DOUBLE NOT NULL DEFAULT 0,
    location VARCHAR(255) NOT NULL,
    size INT,
    is_rent_out BOOLEAN DEFAULT false,
    remarks VARCHAR(255),
    category_id INT UNSIGNED,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    FOREIGN KEY(category_id) REFERENCES category(id)
);

CREATE TABLE `rental_house.appointment` (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone_number VARCHAR(100) NOT NULL,
    appointment_date DATETIME NOT NULL,
    message VARCHAR(255),
    user_id INT UNSIGNED,
    estate_id INT UNSIGNED,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    FOREIGN KEY (user_id) REFERENCES user (id),
	FOREIGN KEY (estate_id) REFERENCES estate (id)
);
