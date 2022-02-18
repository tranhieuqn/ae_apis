ALTER TABLE `order` ADD COLUMN payment_id BIGINT(20) NULL;
ALTER TABLE `order` ADD CONSTRAINT fk__order__payment FOREIGN KEY (payment_id) REFERENCES `payment`(id);
ALTER TABLE `order` ADD COLUMN completed_time TIMESTAMP(3) NULL;

