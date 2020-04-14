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
@TableName("user_group_user")
public class UserGroupUser extends BaseJoinDomain {

    private static final long serialVersionUID=1L;

    private String groupId;

    private String userId;


}
