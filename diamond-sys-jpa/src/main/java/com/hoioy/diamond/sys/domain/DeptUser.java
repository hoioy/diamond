package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseJoinDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 类名称：DeptUser 机构用户中间表
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "dept_user")
public class DeptUser extends BaseJoinDomain {
    private static final long serialVersionUID = 5680225687620614164L;

    @Column(name = "dept_id")
    private String deptId;

    @Column(name = "user_id")
    private String userId;
}