package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseJoinServiceImpl;
import com.hoioy.diamond.sys.domain.UserGroupUser;
import com.hoioy.diamond.sys.dto.UserGroupUserJoinDTO;
import com.hoioy.diamond.sys.mapper.UserGroupUserMapper;
import com.hoioy.diamond.sys.service.IUserGroupUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupUserServiceImpl extends BaseJoinServiceImpl<UserGroupUserMapper, UserGroupUser, UserGroupUserJoinDTO>
        implements IUserGroupUserService<UserGroupUser> {

    @Override
    public List<String> findGroupIdsByUserIds(List<String> userIds) {
        return iBaseMapper.findGroupIdsByUserIds(userIds);
    }
}