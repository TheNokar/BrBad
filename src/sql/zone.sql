CREATE TABLE IF NOT EXISTS `brbad_zone` (
	`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`X` FLOAT NOT NULL,
	`Y` FLOAT NOT NULL,
	`Z` FLOAT NOT NULL,
	`X2` FLOAT NOT NULL,
	`Y2` FLOAT NOT NULL,
	`Z2` FLOAT NOT NULL,
	`world` VARCHAR(255) NOT NULL
);