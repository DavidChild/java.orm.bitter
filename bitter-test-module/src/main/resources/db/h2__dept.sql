CREATE TABLE IF not exists `t_dept`
                           (
                           `dept_id` bigint  AUTO_INCREMENT PRIMARY KEY,
                           `parent_id` bigint(20)  DEFAULT 0,
                           `ancestors` varchar(50),
                           `dept_name` varchar(30),
                           `order_num` int(4) DEFAULT 0,
                           `create_by` varchar(64),
                           `create_time` datetime(0),
                           `update_by` varchar(64),
                           `update_time` datetime(0)
                           )