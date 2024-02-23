 CREATE TABLE `t_dept`
                           (
                           `dept_id` UInt32,
                           `parent_id` Nullable(UInt32),
                           `ancestors` Nullable(String),
                           `dept_name` Nullable(String),
                           `order_num` Nullable(UInt32),
                           `create_by` Nullable(String),
                           `create_time` Nullable(datetime),
                           `update_by` Nullable(String),
                           `update_time` Nullable(datetime)
                           )
                           ENGINE = MergeTree
                            ORDER BY dept_id