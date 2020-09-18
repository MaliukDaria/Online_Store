CREATE SCHEMA `online_store` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `online_store`.`products` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`product_id`));

  INSERT INTO `online_store`.`products` (`name`, `price`) VALUES ('Samsung', '500');