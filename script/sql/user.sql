DROP TABLE user IF EXISTS;

CREATE TABLE `user`
(
    `id`       bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uid`      bigint NOT NULL COMMENT 'uid',
    `nickname` varchar(30) DEFAULT NULL COMMENT '用户名',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;