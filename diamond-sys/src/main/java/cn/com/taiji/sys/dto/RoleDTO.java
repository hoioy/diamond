
package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 类名称：RoleDTO   角色dto
 */
@Data
@NoArgsConstructor
public class RoleDTO extends BaseDTO {

    private static final long serialVersionUID = 8089520763539561510L;

    private String roleDesc;

    private Integer roleIndex;

    private Integer state;

    //	@Size(min=4, max=20, message="角色名长度只能在4-20之间")
    private String roleName;

    private Integer showUsers;

    private Date createTime;

    private String creatorId;

    private Date updateTime;

    private String updaterId;
}