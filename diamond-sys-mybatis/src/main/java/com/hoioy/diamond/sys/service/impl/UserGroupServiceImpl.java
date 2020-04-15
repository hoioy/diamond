package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.sys.domain.UserGroup;
import com.hoioy.diamond.sys.dto.UserGroupDTO;
import com.hoioy.diamond.sys.mapper.UserGroupMapper;
import com.hoioy.diamond.sys.mapper.UserGroupUserMapper;
import com.hoioy.diamond.sys.service.IUserGroupService;
import com.hoioy.diamond.common.util.DiamondMybatisPageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroupMapper, UserGroup, UserGroupDTO> implements IUserGroupService<UserGroup> {

    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private UserGroupUserMapper userGroupUserMapper;

    @Override
    public PageDTO getPage(PageDTO pageDTO) {
        UserGroup userGroup = DiamondMybatisPageUtil.getBean(pageDTO, UserGroup.class);
        IPage<UserGroup> data = userGroupMapper.selectPage(DiamondMybatisPageUtil.getPage(pageDTO), userGroup);
        return DiamondMybatisPageUtil.getPageDTO(data);
    }

    @Override
    public List<String> findUserIdsByGroupIds(List<String> groupIds) {
        return userGroupUserMapper.findUserIdsByGroupIds(groupIds);
    }
}
