
package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * 类名称：RoleDTO   角色dto
 */
@Data
@NoArgsConstructor
public class RoleDTO extends BaseDTO {

    private static final long serialVersionUID = 8089520763539561510L;
    @ApiModelProperty(value = "角色描述")
    private String roleDescription;

    @ApiModelProperty(value = "角色排序")
    private Integer roleIndex;

    @Size(min=2, max=200, message="角色名长度只能在2-200之间")
    @ApiModelProperty(value = "角色名称")
    private String roleName;
}