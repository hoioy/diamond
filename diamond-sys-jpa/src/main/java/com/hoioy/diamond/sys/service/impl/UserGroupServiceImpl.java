package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.sys.domain.UserGroup;
import com.hoioy.diamond.sys.domain.UserGroupRepository;
import com.hoioy.diamond.sys.dto.UserGroupDTO;
import com.hoioy.diamond.sys.service.IUserGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroupRepository, UserGroup, UserGroupDTO> implements IUserGroupService<UserGroup> {

}
