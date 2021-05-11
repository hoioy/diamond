package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户组dto
 */
@Data
@NoArgsConstructor
public class UserGroupDTO extends BaseDTO {
    private static final long serialVersionUID = -3861976282974227768L;

    //	@Size (min=4, max=20, message="用户名长度只能在4-20之间")
    @ApiModelProperty(value = "用户组名称")
    private String groupName;
    @ApiModelProperty(value = "用户组排序")
    private Integer groupIndex;
    @ApiModelProperty(value = "用户组状态")
    private String state;
}
