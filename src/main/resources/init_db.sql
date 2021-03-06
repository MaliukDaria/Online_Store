CREATE SCHEMA `online_store` DEFAULT CHARACTER SET utf8;

CREATE TABLE `online_store`.`products`
(
    `product_id` BIGINT(11)   NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(255) NOT NULL,
    `price`      DOUBLE       NOT NULL,
    `isDeleted`  TINYINT      NOT NULL DEFAULT 0,
    PRIMARY KEY (`product_id`)
);

CREATE TABLE `online_store`.`users`
(
    `user_id`   bigint(11)     NOT NULL AUTO_INCREMENT,
    `login`     varchar(255)   NOT NULL,
    `password`  varchar(255)   NOT NULL,
    `name`      varchar(255)            DEFAULT NULL,
    `isDeleted` tinyint        NOT NULL DEFAULT '0',
    `salt`      varbinary(255) NOT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `online_store`.`shopping_carts`
(
    `shopping_cart_id` bigint  NOT NULL AUTO_INCREMENT,
    `user_id`          bigint  NOT NULL,
    `isDeleted`        tinyint NOT NULL DEFAULT '0',
    PRIMARY KEY (`shopping_cart_id`),
    KEY `shopping_carts_users_fk_idx` (`user_id`),
    CONSTRAINT `shopping_carts_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `online_store`.`orders`
(
    `order_id`  bigint  NOT NULL AUTO_INCREMENT,
    `user_id`   bigint  NOT NULL,
    `isDeleted` tinyint NOT NULL DEFAULT '0',
    PRIMARY KEY (`order_id`),
    KEY `order_user_fk_idx` (`user_id`),
    CONSTRAINT `order_user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `online_store`.`roles`
(
    `role_id` BIGINT(11)   NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(255) NOT NULL,
    PRIMARY KEY (`role_id`)
);

CREATE TABLE `online_store`.`users_roles`
(
    `user_id` BIGINT(11) NOT NULL,
    `role_id` BIGINT(11) NOT NULL,
    INDEX `role_user_fk_idx` (`user_id` ASC) VISIBLE,
    INDEX `users_roles_roles_fk_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `users_roles_user_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `online_store`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `users_roles_roles_fk`
        FOREIGN KEY (`role_id`)
            REFERENCES `online_store`.`roles` (`role_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `online_store`.`orders_products`
(
    `order_id`   BIGINT(11) NOT NULL,
    `product_id` BIGINT(11) NOT NULL,
    INDEX `orders_products_order_fk_idx` (`order_id` ASC) VISIBLE,
    INDEX `orders_products_product_fk_idx` (`product_id` ASC) VISIBLE,
    CONSTRAINT `orders_products_order_fk`
        FOREIGN KEY (`order_id`)
            REFERENCES `online_store`.`orders` (`order_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `orders_products_product_fk`
        FOREIGN KEY (`product_id`)
            REFERENCES `online_store`.`products` (`product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `online_store`.`shopping_carts_products`
(
    `shopping_cart_id` BIGINT(11) NOT NULL,
    `product_id`       BIGINT(11) NOT NULL,
    INDEX `shopping_carts_products_cart_fk_idx` (`shopping_cart_id` ASC) VISIBLE,
    INDEX `shopping_carts_products_products_idx` (`product_id` ASC) VISIBLE,
    CONSTRAINT `shopping_carts_products_cart_fk`
        FOREIGN KEY (`shopping_cart_id`)
            REFERENCES `online_store`.`shopping_carts` (`shopping_cart_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `shopping_carts_products_products`
        FOREIGN KEY (`product_id`)
            REFERENCES `online_store`.`products` (`product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

INSERT INTO `online_store`.`roles` (`role_id`, `name`)
VALUES ('1', 'ADMIN');
INSERT INTO `online_store`.`roles` (`role_id`, `name`)
VALUES ('2', 'USER');
