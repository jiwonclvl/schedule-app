CREATE TABLE `schedules` (
                             `id`	BIGINT	NOT NULL,
                             `user_id`	BIGINT	NOT NULL,
                             `title`	VARCHAR (15)	NOT NULL,
                             `contents`	VARCHAR (50)	NULL,
                             `created_date`	DATETIME	NOT NULL,
                             `updated_date`	DATETIME	NOT NULL
);

CREATE TABLE `users` (
                         `id`	BIGINT	NOT NULL,
                         `username`	VARCHAR (10)	NOT NULL,
                         `email`	VARCHAR (15)	NOT NULL,
                         `password`	VARCHAR (30)	NOT NULL,
                         `created_date`	DATETIME	NOT NULL,
                         `updated_date`	DATETIME	NOT NULL
);

ALTER TABLE `schedules`
    ADD CONSTRAINT `PK_SCHEDULES`
        PRIMARY KEY (`id`, `user_id`);

ALTER TABLE `users`
    ADD CONSTRAINT `PK_USERS`
        PRIMARY KEY (`id`);

ALTER TABLE `schedules`
    ADD CONSTRAINT `FK_users_TO_schedules_1`
        FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);