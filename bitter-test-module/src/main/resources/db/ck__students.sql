CREATE TABLE `t_student`(
id UInt32,
name Nullable(String),
sex_name Nullable(String)
)
ENGINE = MergeTree
ORDER BY id