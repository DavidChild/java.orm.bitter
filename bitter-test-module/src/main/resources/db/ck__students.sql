 CREATE TABLE `t_student`(
id UInt32,
name String,
sex_name String
)
ENGINE = MergeTree
ORDER BY id