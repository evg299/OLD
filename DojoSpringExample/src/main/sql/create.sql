CREATE TABLE `users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `recordUuid` VARCHAR(64) NULL,
  `lastUpdate` DATETIME NULL,
  `fname` VARCHAR(128) NULL,
  `sname` VARCHAR(128) NULL,
  `lname` VARCHAR(128) NULL,
  `email` VARCHAR(128) NULL,
  `gender` TINYINT NULL,
  `birthDate` DATETIME NULL,
  PRIMARY KEY (`id`));
