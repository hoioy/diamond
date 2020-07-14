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
            throw new SysException("所选用户组关联了用户，不能删除！请先删除与用户的关联");
        }
        if (CollectionUtil.isNotEmpty(iRoleGroupService.findSecondIdsByFirstIds(ids))) {
            throw new SysException("所选用户组关联了角色，不能删除！请先删除与角色的关联");
        }
    }
}
