CREATE TABLE `order_item`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `order_id`   bigint(20) NOT NULL,
    `code`       varchar(50),
    `quantity`   int,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);