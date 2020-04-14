package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseJoinDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 类名称：RoleUser  角色用户中间表
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "role_user")
public class RoleUser extends BaseJoinDomain {

    private static final long serialVersionUID = -5549613676858069574L;
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "user_id")
    private String userId;
}