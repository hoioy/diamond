package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.sys.domain.Role;
import com.hoioy.diamond.sys.domain.RoleRepository;
import com.hoioy.diamond.sys.dto.RoleDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.IRoleGroupService;
import com.hoioy.diamond.sys.service.IRoleMenuService;
import com.hoioy.diamond.sys.service.IRoleService;
import com.hoioy.diamond.sys.service.IRoleUserService;
import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类名称：角色Service
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleRepository, Role, RoleDTO> implements IRoleService<Role> {

    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private IRoleMenuService iRoleMenuService;

    @Autowired
    private IRoleGroupService iRoleGroupService;

    /**
     * 根据角色名称查询角色详情
     */
    @Override
    public RoleDTO findByRoleName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        Role role = this.iBaseRepository.findByRoleName(name);
        return domainToDTO(role);
    }

    @Override
    public void beforeRemove(List<String> ids) {
        super.beforeRemove(ids);
        if (CollectionUtil.isNotEmpty(iRoleMenuService.findRoleIdsByMenuIds(ids))) {
            throw new SysException(messageSource.getMessage("exception.containMenu", null,  LocaleContextHolder.getLocale()));
        }
        if (CollectionUtil.isNotEmpty(iRoleGroupService.findFirstIdsBySecondIds(ids))) {
            throw new SysException(messageSource.getMessage("exception.containGroup", null,  LocaleContextHolder.getLocale()));
        }
        if (CollectionUtil.isNotEmpty(iRoleUserService.findUserIdsByRoleIds(ids))) {
            throw new SysException(messageSource.getMessage("exception.containUser", null,  LocaleContextHolder.getLocale()));
        }
    }
}
