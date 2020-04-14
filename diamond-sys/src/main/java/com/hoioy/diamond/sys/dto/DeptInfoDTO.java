package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

//import javax.validation.constraints.Size;


/**
 * 类名称：DeptInfoDTO   机构单位dto
 */
@ApiModel(value = "机构单位Dept视图对象:DeptInfoDTO", description = "机构单位Dept视图对象:DeptInfoDTO")
@Data
@NoArgsConstructor
public class DeptInfoDTO extends BaseDTO {

    private static final long serialVersionUID = 8632051872528629002L;

    private String deptDesc;

    private Integer deptIndex;

    @ApiModelProperty(value = "机构名称", name = "deptName", example = "创新研究院")
    @NotBlank(message = "部门名称不能为空")
    private String deptName;

    private String deptState;

    private String deptType;

    private String deptUrl;

    private String parentName;

}
