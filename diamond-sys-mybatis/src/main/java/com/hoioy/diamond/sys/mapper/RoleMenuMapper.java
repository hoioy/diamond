package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseJoinMapper;
import com.hoioy.diamond.sys.domain.RoleMenu;

import java.util.List;

public interface RoleMenuMapper extends IBaseJoinMapper<RoleMenu> {

    void deleteByMenuIdsAndRoleId(List<String> menuIds, String roleId);
}
