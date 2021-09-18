create database qqrobot;

create table if not exists t_user
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

create table if not exists t_statistic_group
(
    id         int auto_increment
        primary key,
    group_code varchar(20)          not null,
    active     tinyint(1) default 1 null comment '当前统计是否被激活',
    name       varchar(30)          not null
);

create index GROUP_CODE_INDEX
    on t_statistic_group (group_code);


create table if not exists t_statistic
(
    id                 int auto_increment
        primary key,
    content            varchar(100) not null,
    qq                 varchar(20)  not null,
    statistic_group_id int          not null
);

create index T_STATISTIC_GROUP_ID
    on t_statistic (statistic_group_id);


