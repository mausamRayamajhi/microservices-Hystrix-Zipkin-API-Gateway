CREATE TABLE user
(
    user_id       BIGINT NOT NULL,
    first_name    VARCHAR(255) NULL,
    last_name     VARCHAR(255) NULL,
    email         VARCHAR(255) NULL,
    department_id BIGINT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id)
);