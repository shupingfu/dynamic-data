CREATE TABLE IF NOT EXISTS `article`(
`id` INT NOT NULL AUTO_INCREMENT,
`title` VARCHAR(100) NOT NULL,
`author` VARCHAR(40) NOT NULL,
`date` DATE DEFAULT NULL,
PRIMARY KEY ( `id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into schema1.article(id, title, author, `date`) values (1, 'schema1 title', 'schema1 author', now());
insert into schema2.article(id, title, author, `date`) values (1, 'schema2 title', 'schema2 author', now());


-- auto-generated definition
create table IF NOT EXISTS sys_user
(
    user_id        bigint auto_increment
        primary key,
    username       varchar(50)  not null comment '用户名',
    password       varchar(100) null comment '密码',
    salt           varchar(20)  null comment '盐',
    email          varchar(100) null comment '邮箱',
    mobile         varchar(100) null comment '手机号',
    status         tinyint      null comment '状态  0：禁用   1：正常',
    create_user_id bigint       null comment '创建者ID',
    create_time    datetime     null comment '创建时间',
    constraint username
        unique (username)
)
    comment '系统用户' charset = utf8;
INSERT INTO `sys_user` (`user_id`, `username`, `password`, `salt`, `email`, `mobile`, `status`, `create_user_id`,`create_time`)
VALUES ('2', 'admin2', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', '1', '1', '2016-11-11 11:11:11');