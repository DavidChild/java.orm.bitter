 create table  `t_user`
                           (
                           `id` String  ,
                           `username` Nullable(String),
                           `email` Nullable(String),
                           `phone`    Nullable(String)  ,
                           `password`  Nullable(String)  ,
                           `nickname`  Nullable(String)  ,
                           `avatar`    Nullable(String)  ,
                           `telephone` Nullable(String) ,
                           `create_by` Nullable(String),
                            `create_time` Nullable(dateTime) ,
                           `update_by` Nullable(String),
                           `update_time` Nullable(dateTime),
                           `dept_id` Nullable(UInt32),
                           `user_age` Nullable(UInt32)
                           )
ENGINE = MergeTree
ORDER BY id