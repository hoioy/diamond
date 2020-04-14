package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.TDFDomain;
import com.hoioy.diamond.common.service.IBaseJoinService;
import com.hoioy.diamond.sys.dto.RoleMenuJoinDTO;

import java.util.List;

public interface IRoleMenuService<D extends TDFDomain> extends IBaseJoinService<RoleMenuJoinDTO, D> {

    /**
     * 保存角色菜单的分配
     */
    void saveRoleMenusForRest(String[] menuIds, List preMenuIds, String roleId);

    List<String> findRoleIdsByMenuIds(List<String> menuIds);

    /**
     * 根据roleId查询出关系表中所有的菜单Id
     */
    List<String> findMenuIdsByRoleIds(List<String> roleIds);
}
