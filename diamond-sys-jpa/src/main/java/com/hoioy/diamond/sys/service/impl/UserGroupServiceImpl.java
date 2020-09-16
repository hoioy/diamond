package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.sys.domain.UserGroup;
import com.hoioy.diamond.sys.domain.UserGroupRepository;
import com.hoioy.diamond.sys.dto.UserGroupDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.IRoleGroupService;
import com.hoioy.diamond.sys.service.IUserGroupService;
import com.hoioy.diamond.sys.service.IUserGroupUserService;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroupRepository, UserGroup, UserGroupDTO> implements IUserGroupService<UserGroup> {
    @Autowired
    private IUserGroupUserService iUserGroupUserService;

    @Autowired
    private IRoleGroupService iRoleGroupService;

    @Override
    public void beforeRemove(List<String> ids) {
        super.beforeRemove(ids);
        if (CollectionUtil.isNotEmpty(iUserGroupUserService.findUserIdsByGroupIds(ids))) {
            throw new SysException(messageSource.getMessage("exception.containUser", null,  LocaleContextHolder.getLocale()));
        }
        if (CollectionUtil.isNotEmpty(iRoleGroupService.findSecondIdsByFirstIds(ids))) {
            throw new SysException(messageSource.getMessage("exception.containRole", null,  LocaleContextHolder.getLocale()));
        }
    }
}
