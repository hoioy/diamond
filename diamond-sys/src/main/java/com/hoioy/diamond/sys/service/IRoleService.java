package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.TDFDomain;
import com.hoioy.diamond.sys.dto.RoleDTO;
import com.hoioy.diamond.common.service.IBaseService;

import java.util.List;

/**
 * 类名称：角色Service
 */
public interface IRoleService<D extends TDFDomain> extends IBaseService<RoleDTO, D> {
    /**
     * 查询所有角色
     */
    List<RoleDTO> findAll();

    /**
     * 根据角色名称查询角色详情
     */
    RoleDTO findByRoleName(String name);
}
