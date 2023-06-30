
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
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
`user_age` bigint(4)NOT NULL,
PRIMARY KEY(`id`)
USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;
