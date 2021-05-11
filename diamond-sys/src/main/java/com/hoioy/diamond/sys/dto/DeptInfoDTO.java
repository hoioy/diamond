package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseTreeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/**
 * 类名称：DeptInfoDTO   机构单位dto
 */
@ApiModel(value = "机构单位Dept视图对象:DeptInfoDTO", description = "机构单位Dept视图对象:DeptInfoDTO")
@Data
@NoArgsConstructor
public class DeptInfoDTO extends BaseTreeDTO{

    private static final long serialVersionUID = 8632058629002L;
    @ApiModelProperty(value = "机构描述")
    private String deptDesc;
    @ApiModelProperty(value = "机构排序字段")
    private Integer deptIndex;

    @ApiModelProperty(value = "机构名称", name = "deptName", example = "创新研究院")
    @NotBlank(message = "机构名称不能为空")
    private String deptName;
    @ApiModelProperty(value = "机构状态", name = "deptState", example = "1")
    private String deptState;
    @ApiModelProperty(value = "机构类型")
    private String deptType;
    @ApiModelProperty(value = "机构类型")
    private String deptUrl;
    @ApiModelProperty(value = "上级机构名称")
    private String parentName;

}
