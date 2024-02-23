package io.github.davidchild.bitter.test.tools;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @data 2023/11/22 17:08
 * 基础数据
 */
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class BaseDataSum {

    @ApiModelProperty(value = "注册用户数")
    private Integer regUserNumber;

    @ApiModelProperty(value = "简历数量")
    private Integer resumeNumber;

    @ApiModelProperty(value = "企业用户数")
    private Integer regEnterpriseNumber;

    @ApiModelProperty(value = "付费企业数")
    private Integer payEnterpriseNumber;

    @ApiModelProperty(value = "岗位总数")
    private Integer releaseNumber;

    @ApiModelProperty(value = "新增用户数")
    private Integer addUserNumber;

    @ApiModelProperty(value = "新增企业数")
    private Integer addEnterpriseNumber;

    @ApiModelProperty(value = "新增简历数")
    private Integer addResumeNumber;

    @ApiModelProperty(value = "新增岗位数")
    private Integer addReleaseNumber;


    private Integer addPayEnterpriseNumber;

}
