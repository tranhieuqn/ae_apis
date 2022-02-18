CREATE TABLE IF NOT EXISTS `payment` (
    `id`                    BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `transaction_id`        VARCHAR(250) NULL,
    `type`                  VARCHAR(50) NULL,
    `status`                VARCHAR(50) NULL,
    `fee`                   DECIMAL(20,5) UNSIGNED NULL,
    `original_fee`          DECIMAL(20,5) UNSIGNED NULL,
    `description`           TEXT NULL,
    `env`                   VARCHAR(250) NULL,
    `created_date`          TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    `modified_date`         TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX id_idx ON `payment` (id);


CREATE TABLE IF NOT EXISTS `payment_refund_history` (
    `id`                    BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `payment_id`            BIGINT(20) NULL,
    `type`                  VARCHAR(50) NULL,
    `status`                VARCHAR(50) NULL,
    `amount`                DECIMAL(20,5) UNSIGNED NULL,
    `description`           TEXT NULL,
    `created_date`          TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    `modified_date`         TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    FOREIGN KEY `fk__payment_refund_history__payment`(`payment_id`) REFERENCES `payment`(`id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX id_idx ON `payment_refund_history` (id);


CREATE TABLE IF NOT EXISTS `payment_vnp` (
    `id`                    BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `payment_id`            BIGINT(20) NULL,
    `vnp_ip_addr`           VARCHAR(250) NULL,
    `vnp_txn_ref`           VARCHAR(250) NULL,
    `vnp_tmn_code`          VARCHAR(250) NULL,
    `vnp_amount`            DECIMAL(20,5) UNSIGNED NULL,
    `vnp_command`           VARCHAR(250) NULL,
    `vnp_create_date`       TIMESTAMP(3) NULL,
    `vnp_curr_code`         VARCHAR(250) NULL,
    `vnp_locale`            VARCHAR(250) NULL,
    `vnp_version`           VARCHAR(250) NULL,
    `vnp_order_type`        VARCHAR(250) NULL,
    `vnp_order_info`        VARCHAR(250) NULL,
    `account_id`            BIGINT(20) NULL,
    `vnp_bank_code`         VARCHAR(250) NULL,
    `vnp_bank_tran_no`      VARCHAR(250) NULL,
    `vnp_card_type`         VARCHAR(250) NULL,
    `vnp_pay_date`          TIMESTAMP(3) NULL,
    `vnp_response_code`     VARCHAR(250) NULL,
    `vnp_transaction_no`    VARCHAR(250) NULL,
    `created_date`          TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    `modified_date`         TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    FOREIGN KEY `fk__payment_vnp__payment`(`payment_id`) REFERENCES `payment`(`id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX id_idx ON `payment_vnp` (id);

