CREATE TABLE IF not exists `t_user`
                           (
                           `id` varchar(32)   PRIMARY KEY,
                           `username` varchar(100),
                           `email` varchar(45)  DEFAULT NULL COMMENT '电子邮件',
                           `phone` varchar(45)  DEFAULT NULL COMMENT '电话',
                           `password` varchar(255)  DEFAULT NULL COMMENT '密码',
                           `nickname` varchar(255)  DEFAULT NULL COMMENT '昵称',
                           `avatar` varchar(255)  DEFAULT '' COMMENT '头像',
                           `telephone` varchar(45)  DEFAULT NULL COMMENT '座机号',
                           `create_by` varchar(32)  DEFAULT NULL COMMENT '创建人',
                           `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
                           `update_by` varchar(32)  DEFAULT NULL COMMENT '更新人',
                           `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
                           `dept_id` bigint(20) DEFAULT NULL,
                           `user_age` bigint(4) DEFAULT NULL
                           )
