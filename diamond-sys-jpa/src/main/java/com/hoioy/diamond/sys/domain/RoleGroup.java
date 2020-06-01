package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.annotation.BaseJoinId;
import com.hoioy.diamond.common.base.BaseJoinDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色与用户组关联表
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "role_group")
public class RoleGroup extends BaseJoinDomain {

    private static final long serialVersionUID = -1382925073246209455L;

    @Column(name = "group_id")
    @BaseJoinId(index = BaseJoinId.Index.first)
    private String groupId;

    @Column(name = "role_id")
    @BaseJoinId(index = BaseJoinId.Index.second)
    private String roleId;
}