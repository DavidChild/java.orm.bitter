 create table  `t_user`
                           (
                           `id` String  ,
                           `username` String,
                           `email` String,
                           `phone`    String  ,
                           `password`  String  ,
                           `nickname`  String  ,
                           `avatar`    String  ,
                           `telephone` String ,
                           `create_by` String,
                            `create_time` dateTime ,
                           `update_by` String,
                           `update_time` dateTime,
                           `dept_id` UInt32,
                           `user_age` UInt32
                           )
ENGINE = MergeTree
ORDER BY id