package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.sys.domain.Role;
import com.hoioy.diamond.sys.domain.RoleRepository;
import com.hoioy.diamond.sys.dto.RoleDTO;
import com.hoioy.diamond.sys.service.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类名称：角色Service
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleRepository, Role, RoleDTO> implements IRoleService<Role> {
    /**
     * 查询所有角色
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<RoleDTO> findAll() {
        return domainListToDTOList(iBaseRepository.findAll());
    }

    /**
     * 根据角色名称查询角色详情
     */
    //@Cacheable(value = "roleDto",key="#name")
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public RoleDTO findByRoleName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        Role role = this.iBaseRepository.findByRoleName(name);
        return domainToDTO(role);
    }
}
