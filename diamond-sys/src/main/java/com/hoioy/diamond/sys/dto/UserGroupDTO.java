package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
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
    private String groupName;

    private Integer groupIndex;

    private String state;
}
