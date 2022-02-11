CREATE TABLE IF NOT EXISTS `media` (
    `id`              BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `status`          VARCHAR(50) NULL,
    `created_date`    TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    `modified_date`   TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)
    )
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `category` (
    `id`              BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `parent_id`       BIGINT(20) NULL,
    `name`            VARCHAR(255) NULL,
    `description`     VARCHAR(500) NULL,
    `thumbnail`       VARCHAR(255) NULL,
    `status`          VARCHAR(50) NULL,
    `media_id`        BIGINT(20) NULL,
    `created_date`    TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    `modified_date`   TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    FOREIGN KEY `fk__category__media`(`media_id`) REFERENCES `media`(`id`)
    )
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `product` (
    `id`              BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `category_id`       BIGINT(20) NULL,
    `name`            VARCHAR(255) NULL,
    `description`     VARCHAR(500) NULL,
    `thumbnail`       VARCHAR(255) NULL,
    `price`           DECIMAL(20,5) UNSIGNED NULL,
    `status`          VARCHAR(50) NULL,
    `media_id`        BIGINT(20) NULL,
    `created_date`    TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    `modified_date`   TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    FOREIGN KEY `fk__product__category`(`category_id`) REFERENCES `category`(`id`),
    FOREIGN KEY `fk__product__media`(`media_id`) REFERENCES `media`(`id`)
    )
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `property` (
    `id`              BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `product_id`       BIGINT(20) NULL,
    `name`            VARCHAR(255) NULL,
    `description`     VARCHAR(500) NULL,
    `thumbnail`       VARCHAR(255) NULL,
    `status`          VARCHAR(50) NULL,
    `media_id`        BIGINT(20) NULL,
    `created_date`    TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    `modified_date`   TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    FOREIGN KEY `fk__property__product`(`product_id`) REFERENCES `product`(`id`),
    FOREIGN KEY `fk__property__media`(`media_id`) REFERENCES `media`(`id`)
    )
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `media_detail` (
    `id`              BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `media_id`        BIGINT(20) NULL,
    `name`            VARCHAR(255) NULL,
    `description`     VARCHAR(500) NULL,
    `url`             VARCHAR(255) NULL,
    `status`          VARCHAR(50) NULL,
    `created_date`    TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    `modified_date`   TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    FOREIGN KEY `fk__media_detail__media`(`media_id`) REFERENCES `media`(`id`)
    )
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `order` (
    `id`              BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`         BIGINT(20) NULL,
    `ref_number`      VARCHAR(255) NULL,
    `status`          VARCHAR(50) NULL,
    `note`            VARCHAR(500) NULL,
    `phone`           VARCHAR(100) NULL,
    `address`         VARCHAR(300) NULL,
    `order_date`      TIMESTAMP(3) NULL,
    `created_date`    TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    `modified_date`   TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)
    )
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `order_detail` (
    `id`              BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `order_id`        BIGINT(20) NULL,
    `product_id`      BIGINT(20) NULL,
    `property_id`     BIGINT(20) NULL,
    `quantity`        INT NULL,
    `unit_price`      DECIMAL(20,5) NULL,
    `created_date`    TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    `modified_date`   TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    FOREIGN KEY `fk__order_detail__order`(`order_id`) REFERENCES `order`(`id`),
    FOREIGN KEY `fk__order_detail__product`(`product_id`) REFERENCES `product`(`id`),
    FOREIGN KEY `fk__order_detail__property`(`property_id`) REFERENCES `property`(`id`)
    )
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `account` (
    `id`                 BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `phone_number`       VARCHAR(50) NULL UNIQUE,
    `email`              VARCHAR(50) NULL UNIQUE,
    `user_role`          VARCHAR(50) NULL,
    `password`           VARCHAR(70) NULL,
    `first_name`         VARCHAR(250) NULL,
    `last_name`          VARCHAR(250) NULL,
    `avatar_url`         VARCHAR(250) NULL,
    `gender`             VARCHAR(50) NULL,
    `login_status`       VARCHAR(50) NULL,
    `register_status`    VARCHAR(50) NULL,
    `registration_token` VARCHAR(250) NULL,
    `address`            TEXT NULL,
    `created_date`       TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    `modified_date`      TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)
    )
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;