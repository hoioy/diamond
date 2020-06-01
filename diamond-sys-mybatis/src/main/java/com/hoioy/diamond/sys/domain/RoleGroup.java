package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.annotation.BaseJoinId;
import com.hoioy.diamond.common.base.BaseJoinDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色与用户组关联表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role_group")
public class RoleGroup extends BaseJoinDomain {
    private static final long serialVersionUID = 1L;
    @BaseJoinId(index = BaseJoinId.Index.first)
    private String groupId;

    @BaseJoinId(index = BaseJoinId.Index.second)
    private String roleId;
}