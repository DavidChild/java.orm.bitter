package io.github.davidchild.bitter.samples.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.davidchild.bitter.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author davidChild
 * @since 2023-02-07
 */
@Data
@TableName("dipin_async_msg")
@ApiModel(value = "DipinAsyncMsg对象", description = "")
public class DipinAsyncMsg extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @ApiModelProperty(value = "业务ID")
    @TableField("bus_id")
    private String busId;

    @ApiModelProperty(value = "user 筛选条件")
    @TableField("user_where")
    private String userWhere;

    @ApiModelProperty(value = "消息")
    @TableField("msg")
    private String msg;

    @ApiModelProperty(value = "参数")
    @TableField("params")
    private String params;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("modify_time")
    private Date modifyTime;

    @ApiModelProperty(value = "触发当前行为的用户")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "任务最终异常消息")
    @TableField("error_msg")
    private String errorMsg;

    @ApiModelProperty(value = "是否成功")
    @TableField("is_success")
    private Boolean isSuccess;

    @ApiModelProperty(value = "注入消费bean")
    @TableField("consume_bean")
    private String consumeBean;

    @ApiModelProperty(value = "异常id记录")
    @TableField("error_task_id")
    private Integer errorTaskId;

    @ApiModelProperty(value = "0: 初始 1：启动 2：完成  3：异常停止")
    @TableField("status")
    private Integer status;

    @TableField("map_reduces_job_id")
    private String mapReducesJobId;

    @ApiModelProperty(value = "配置表id")
    @TableField("setting_id")
    private Integer settingId;

    @ApiModelProperty(value = "是否为极光推送")
    @TableField("jg_push")
    private Boolean jgPush;


}
