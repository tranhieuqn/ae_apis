CREATE TABLE IF NOT EXISTS `confirmation_token` (
    `id`                 BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `confirmation_token` VARCHAR(250) NULL,
    `user_id`            BIGINT(20) NULL,
    `expire_date`        TIMESTAMP(3) NULL,
    `type`               VARCHAR(50) NULL,
    `created_date`       TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    `modified_date`      TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX id_idx ON `confirmation_token` (id);
CREATE INDEX user_id_idx ON `confirmation_token` (user_id);