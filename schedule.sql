CREATE SCHEMA scheduler;

USE scheduler;

CREATE TABLE user
(
    id         INT PRIMARY KEY AUTO_INCREMENT COMMENT '사용자 식별자',
    name       VARCHAR(50)  NOT NULL,
    email      VARCHAR(100) NOT NULL,
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL
);

CREATE TABLE schedule
(
    id         INT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    user_id    INT          NOT NULL COMMENT '사용자 식별자',
    content    VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL,

    CONSTRAINT fk_schedule_user FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO user(name, email, created_at, updated_at) VALUES('이호준', 'ggg@mail.com', NOW(), NOW());
INSERT INTO user(name, email, created_at, updated_at) VALUES('이개소', 'bbb@mail.com', NOW(), NOW());

INSERT INTO schedule(user_id, content, password, created_at, updated_at) VALUES(1, '숨쉬기', '1234', NOW(), NOW());
INSERT INTO schedule(user_id, content, password, created_at, updated_at) VALUES(1, '눈감기', '1234', NOW(), NOW());
INSERT INTO schedule(user_id, content, password, created_at, updated_at) VALUES(2, '숨쉬기', '1234', NOW(), NOW());
INSERT INTO schedule(user_id, content, password, created_at, updated_at) VALUES(2, '눈감기', '1234', NOW(), NOW());
