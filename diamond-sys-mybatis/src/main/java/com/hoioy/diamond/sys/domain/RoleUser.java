package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.annotation.BaseJoinId;
import com.hoioy.diamond.common.base.BaseJoinDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role_user")
public class RoleUser extends BaseJoinDomain {

    private static final long serialVersionUID = 1L;

    @BaseJoinId(index=BaseJoinId.Index.first)
    private String roleId;

    @BaseJoinId(index=BaseJoinId.Index.second)
    private String userId;
}
