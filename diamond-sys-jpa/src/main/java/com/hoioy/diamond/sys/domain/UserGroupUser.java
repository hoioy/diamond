package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseJoinDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 类名称：UserGroupUser   用户组用户中间表
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "user_group_user")
public class UserGroupUser extends BaseJoinDomain {

    private static final long serialVersionUID = -1382925073246209455L;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "user_id")
    private String userId;
}