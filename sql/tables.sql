# 数据库初始化
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists letty;

-- 切换库
use letty;

# 用户表
create table user
(
    username     varchar(256)                       null comment '用户昵称',
    id           bigint auto_increment comment 'id'
        primary key,
    user_account  varchar(256)                       null comment '账号',
    avatar_url    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别',
    user_password varchar(512)                       not null comment '密码',
    phone        varchar(128)                       null comment '电话',
    email        varchar(512)                       null comment '邮箱',
    user_status   int      default 0                 not null comment '状态 0 - 正常',
    created_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updated_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_deleted     tinyint  default 0                 not null comment '是否删除',
    user_role     int      default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员',
    tags   varchar(1024)                       null comment '用户的标签'
)
    comment '用户';

create table tag
(
    id           bigint auto_increment comment 'id' primary key,
    tag_name     varchar(256)                       null comment '标签名称',
    user_id      bigint                             null comment 'user who created this tag',
    parent_id    bigint                             null comment '父标签id',
    is_parent    tinyint                            null comment '0 - 不是，1 - 父标签',
    created_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updated_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_deleted   tinyint  default 0                 not null
)
    comment '标签';


create table if not exists post (
    id bigint auto_increment comment 'id' primary key,
    title varchar(512) null comment '标题',
    content text null comment '内容',
    tags varchar(1024) null comment '标签列表(json数组)',
    thumb_num int default 0 not null comment '点赞数',
    favour_num int default 0 not null comment '收藏数',
    user_id bigint not null comment '创建用户id',
    created_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted tinyint default 0 not null comment '是否删除',
    index idx_user_id(user_id)
) comment '帖子' collate = utf8mb4_unicode_ci;


create table if not exists team (
                      id bigint auto_increment comment "id" primary key,
                      name varchar(256)   not null comment "队伍名称",
                      description varchar(1024) null comment "描述",
                      max_num int default 1 not null comment "最大人数",
                      expire_time datetime null comment "过期时间",
                      user_id bigint comment "用户id",
                      status int default 0 not null comment "0 公开  1 私有 2 加密",
                      password varchar(512) null comment "密码",
                      created_time datetime default CURRENT_TIMESTAMP null comment "创建时间",
                      updated_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
                      is_deleted tinyint default 0 not null comment "是否删除"
) comment "队伍";

create table if not exists user_team(
                          id bigint auto_increment comment "id" primary key,
                          user_id bigint comment "用户id",
                          team_id bigint comment "队伍id",
                          join_time datetime null comment "加入时间",
                          created_time datetime default CURRENT_TIMESTAMP null comment "创建时间",
                          updated_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
                          is_deleted tinyint default 0 not null comment "是否删除"
) comment '用户队伍';

# # 导入示例用户
# INSERT INTO letty.user (username, user_account, avatar_url, gender, user_password, phone, email, user_status, created_time, updated_time, isDeleted, user_role, planet_code) VALUES ('鱼皮', 'letty', 'https://himg.bdimg.com/sys/portraitn/item/public.1.e137c1ac.yS1WqOXfSWEasOYJ2-0pvQ', null, 'b0dd3697a192885d7c055db46155b26a', null, null, 0, '2023-08-06 14:14:22', '2023-08-06 14:39:37', 0, 1, '1');

# [加入编程导航](https://t.zsxq.com/0emozsIJh) 入门捷径+交流答疑+项目实战+求职指导，帮你自学编程不走弯路









