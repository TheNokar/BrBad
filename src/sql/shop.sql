CREATE TABLE IF NOT EXISTS `brbad_shop` (
	`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`price` INTEGER NOT NULL,
	`amount` INTEGER NOT NULL,
	`playerUUID` VARCHAR(255) NOT NULL,
	`X` FLOAT NOT NULL,
	`Y` FLOAT NOT NULL,
	`Z` FLOAT NOT NULL,
	`world` VARCHAR(255) NOT NULL,
	`item_name` VARCHAR(255) NOT NULL,
	`item_id` INTEGER NOT NULL,
	`item_data` INTEGER NOT NULL,
	`shop_type` VARCHAR(255) NOT NULL
);