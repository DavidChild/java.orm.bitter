package io.github.davidchild.bitter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.github.davidchild.bitter.BaseModel;
import lombok.Data;

@Data
@TableName("student")
public class TStudentInfo extends BaseModel {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("sex")
    private String sex;

}
