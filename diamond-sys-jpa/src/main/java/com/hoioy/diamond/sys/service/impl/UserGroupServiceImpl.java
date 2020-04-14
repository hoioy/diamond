package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.sys.domain.UserGroup;
import com.hoioy.diamond.sys.domain.UserGroupRepository;
import com.hoioy.diamond.sys.domain.UserGroupUserRepository;
import com.hoioy.diamond.sys.dto.UserGroupDTO;
import com.hoioy.diamond.sys.service.IUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.PageRequest.of;

/**
 * 类名称：用户组Group Service 类描述： 创建人：wanghw 创建时间：2018年2月5日 下午8:30:29
 */
@Service
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroupRepository, UserGroup, UserGroupDTO> implements IUserGroupService<UserGroup> {

    @Autowired
    private UserGroupUserRepository userGroupUserRepository;

    @Override
    public List<String> findUserIdsByGroupIds(List<String> groupIds) {
        return userGroupUserRepository.findUserIdsByGroupIds(groupIds);
    }
}
