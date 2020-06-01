package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.annotation.BaseJoinId;
import com.hoioy.diamond.common.base.BaseJoinDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("dept_user")
public class DeptUser extends BaseJoinDomain {

    private static final long serialVersionUID = 1L;

    @BaseJoinId(index=BaseJoinId.Index.first)
    private String deptId;

    @BaseJoinId(index=BaseJoinId.Index.second)
    private String userId;


}
