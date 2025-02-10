CREATE TABLE `members` (
                         `id`	BIGINT	NOT NULL,
                         `membername`	VARCHAR (10)	NOT NULL,
                         `email`	VARCHAR (50)	NOT NULL UNIQUE,
                         `password`	VARCHAR (30)	NOT NULL,
                         `created_date`	DATETIME	NOT NULL,
                         `updated_date`	DATETIME	NOT NULL
);

CREATE TABLE `schedules` (
                             `id`	BIGINT	NOT NULL,
                             `member_id`	BIGINT	NOT NULL,
                             `title`	VARCHAR (15)	NOT NULL,
                             `contents`	VARCHAR (50)	NULL,
                             `created_date`	DATETIME	NOT NULL,
                             `updated_date`	DATETIME	NOT NULL
);

ALTER TABLE `schedules`
    ADD CONSTRAINT `PK_SCHEDULES`
        PRIMARY KEY (`id`, `member_id`);

ALTER TABLE `members`
    ADD CONSTRAINT `PK_USERS`
        PRIMARY KEY (`id`);

ALTER TABLE `schedules`
    ADD CONSTRAINT `FK_users_TO_schedules_1`
        FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE;

CREATE TABLE `comments` (
                         `id`	BIGINT	NOT NULL,
                         `member_id`	BIGINT NOT NULL,
                         `schedule_id`	BIGINT	NOT NULL,
                         `comment` VARCHAR (50),
                         `created_date`	DATETIME	NOT NULL,
                         `updated_date`	DATETIME	NOT NULL
);

ALTER TABLE `comments`
    ADD CONSTRAINT `FK_users_TO_comments`
        FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE;

ALTER TABLE `comments`
    ADD CONSTRAINT `FK_schedules_TO_comments_2`
        FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`id`) ON DELETE CASCADE;

