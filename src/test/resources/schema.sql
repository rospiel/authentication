SET REFERENTIAL_INTEGRITY FALSE;

DROP TABLE IF EXISTS `user` cascade;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `dateRegistration` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `group` cascade;

CREATE TABLE `group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user_group`;

CREATE TABLE `user_group` (
  `user_id` bigint NOT NULL,
  `group_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`, `group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `user_group` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `user_group` ADD FOREIGN KEY (`group_id`) REFERENCES `group` (`id`);

DROP TABLE IF EXISTS `permission` cascade;

CREATE TABLE `permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `group_permission`;

CREATE TABLE `group_permission` (
  `group_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  PRIMARY KEY (`group_id`, `permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `group_permission` ADD FOREIGN KEY (`group_id`) REFERENCES `group` (`id`);
ALTER TABLE `group_permission` ADD FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`);


SET REFERENTIAL_INTEGRITY TRUE;