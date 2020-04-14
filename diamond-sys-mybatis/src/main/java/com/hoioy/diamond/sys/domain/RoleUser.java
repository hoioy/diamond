package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseJoinDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role_user")
public class RoleUser extends BaseJoinDomain {

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String userId;


}
