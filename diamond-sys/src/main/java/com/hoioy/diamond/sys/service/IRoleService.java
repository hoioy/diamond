package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.sys.dto.RoleDTO;

/**
 * 类名称：角色Service
 */
public interface IRoleService<D extends CommonDomain> extends IBaseService<RoleDTO, D> {
    /**
     * 根据角色名称查询角色详情
     */
    RoleDTO findByRoleName(String name);
}
