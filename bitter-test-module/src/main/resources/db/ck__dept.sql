 CREATE TABLE `t_dept`
                           (
                           `dept_id` UInt32,
                           `parent_id` UInt32,
                           `ancestors` String,
                           `dept_name` String,
                           `order_num` UInt32,
                           `create_by` String,
                           `create_time` datetime,
                           `update_by` String,
                           `update_time` datetime
                           )
                           ENGINE = MergeTree
                            ORDER BY dept_id