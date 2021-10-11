CREATE TABLE department
(
    department_id      BIGINT NOT NULL,
    department_name    VARCHAR(255) NULL,
    department_address VARCHAR(255) NULL,
    department_code    VARCHAR(255) NULL,
    CONSTRAINT pk_department PRIMARY KEY (department_id)
);