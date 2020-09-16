package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.sys.domain.Role;
import com.hoioy.diamond.sys.dto.RoleDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.mapper.RoleMapper;
import com.hoioy.diamond.sys.service.IRoleGroupService;
import com.hoioy.diamond.sys.service.IRoleMenuService;
import com.hoioy.diamond.sys.service.IRoleService;
import com.hoioy.diamond.sys.service.IRoleUserService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role, RoleDTO> implements IRoleService<Role> {
    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private IRoleMenuService iRoleMenuService;

    @Autowired
    private IRoleGroupService iRoleGroupService;

    @Override
    public PageDTO<RoleDTO> getPage(PageDTO<RoleDTO> pageDTO) {
        Role role = getDomainFilterFromPageDTO(pageDTO);
        IPage<Role> data = iBaseRepository.selectPage(CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO), role);
        return CommonMybatisPageUtil.getInstance().iPageToPageDTO(data,RoleDTO.class);
    }

    @Override
    public RoleDTO findByRoleName(String name) {
        Role role = iBaseRepository.findByRoleName(name);
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
