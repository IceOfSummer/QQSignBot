create database qqrobot;

create table t_user
(
    qq                    varchar(20)          not null,
    last_sign             date                 not null,
    consecutive_sign_days int        default 0 null,
    sign_ignore           tinyint(1) default 0 null,
    group_code            varchar(20)          not null,
    role                  int        default 1 null comment '1:普通用户, 2:管理员, 3:超级管理员'
);

create index GROUP_INDEX
    on t_user (group_code);

create index QQ_INDEX
    on t_user (qq);

create table t_group
(
    group_code varchar(20) not null
        primary key
);


