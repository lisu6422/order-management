CREATE TABLE `order`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `order_no`         varchar(50),
    `status`           varchar(16),
    `created_by`       varchar(20),
    `preparation_time` int,
    `created_at`       datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`       datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_order_no` (`order_no`)
);