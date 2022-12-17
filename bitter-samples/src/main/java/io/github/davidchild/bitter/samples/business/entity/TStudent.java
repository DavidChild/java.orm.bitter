package io.github.davidchild.bitter.samples.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.davidchild.bitter.BaseModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author davidChild
 * @since 2022-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_student")
@ApiModel(value = "TStudent对象", description = "")
public class TStudent extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("sex")
    private String sex;


}
