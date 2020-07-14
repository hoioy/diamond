package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.sys.domain.UserGroup;
import com.hoioy.diamond.sys.dto.UserGroupDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.mapper.UserGroupMapper;
import com.hoioy.diamond.sys.service.*;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroupMapper, UserGroup, UserGroupDTO> implements IUserGroupService<UserGroup> {

    @Autowired
    private IUserGroupUserService iUserGroupUserService;

    @Autowired
    private IRoleGroupService iRoleGroupService;

    @Override
    public PageDTO<UserGroupDTO> getPage(PageDTO<UserGroupDTO> pageDTO) {
        UserGroup userGroup = getDomainFilterFromPageDTO(pageDTO);
        IPage<UserGroup> data = iBaseRepository.selectPage(CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO), userGroup);
        return CommonMybatisPageUtil.getInstance().iPageToPageDTO(data,UserGroupDTO.class);
    }

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
