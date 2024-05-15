DROP DATABASE IF EXISTS beerservice;
DROP USER IF EXISTS `beer_service`@`%`;
CREATE DATABASE IF NOT EXISTS beerservice CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `beer_service`@`%` IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON `beerservice`.* TO `beer_service`@`%`;
FLUSH PRIVILEGES;
