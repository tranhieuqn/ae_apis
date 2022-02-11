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