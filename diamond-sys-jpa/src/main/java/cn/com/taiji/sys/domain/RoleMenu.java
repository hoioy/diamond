package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseJoinDomain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 类名称：RoleMenu   角色菜单中间表
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "role_menu")
@NamedQuery(name = "RoleMenu.findAll", query = "SELECT r FROM RoleMenu r")
public class RoleMenu extends BaseJoinDomain {

    private static final long serialVersionUID = -3204190138251678123L;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "menu_id")
    private String menuId;
}