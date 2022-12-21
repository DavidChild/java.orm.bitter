/ *
Navicat Premium Data Transfer
Source Server Type : MySQL
Source Server Version : 50737
Target Server Type : MySQL
Target Server Version : 50737 File Encoding         : 65001 Date : 28 / 11 / 2022 14:22:23
* /

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student`
(
`id` bigint(255
)
NOT NULL AUTO_INCREMENT,
`name` varchar
(
255
)
CHARACTER
SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
`sex` varchar(255)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
PRIMARY KEY(`id`)
USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
