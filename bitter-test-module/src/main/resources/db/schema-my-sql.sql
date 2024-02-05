DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept`
(
`dept_id` bigint(20)NOT NULL AUTO_INCREMENT COMMENT '部门id',
`parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
`ancestors` varchar(50)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '祖级列表',
`dept_name` varchar(30)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
`order_num` int(4)NULL DEFAULT 0 COMMENT '显示顺序',
`create_by` varchar(64)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
`create_time` datetime(0)NULL DEFAULT NULL COMMENT '创建时间',
`update_by` varchar(64)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
`update_time` datetime(0)NULL DEFAULT NULL COMMENT '更新时间',
PRIMARY KEY(`dept_id`)
)

DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student`(
`id` bigint(255) NOT NULL AUTO_INCREMENT,
`name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
`sex_name` varchar(255)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,PRIMARY KEY(`id`)
 )

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
`id` varchar(32) CHARACTER
 SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
`username` varchar(100)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
`email` varchar(45)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
`phone` varchar(45)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
`password` varchar(255)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
`nickname` varchar(255)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
`avatar` varchar(255)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像',
`telephone` varchar(45)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '座机号',
`create_by` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
`create_time` datetime(0)NULL DEFAULT NULL COMMENT '创建时间',
`update_by` varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
`update_time` datetime(0)NULL DEFAULT NULL COMMENT '更新时间',
`dept_id` bigint(20)NULL DEFAULT NULL,
`user_age` bigint(4)NOT NULL,PRIMARY KEY(`id`)
)

