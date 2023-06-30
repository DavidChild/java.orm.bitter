SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
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
PRIMARY KEY(`dept_id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 214 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;
