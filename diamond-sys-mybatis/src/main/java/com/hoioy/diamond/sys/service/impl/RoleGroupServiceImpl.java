package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseJoinServiceImpl;
import com.hoioy.diamond.sys.domain.RoleGroup;
import com.hoioy.diamond.sys.dto.RoleGroupJoinDTO;
import com.hoioy.diamond.sys.mapper.RoleGroupMapper;
import com.hoioy.diamond.sys.service.IRoleGroupService;
import org.springframework.stereotype.Service;

@Service
public class RoleGroupServiceImpl extends BaseJoinServiceImpl<RoleGroupMapper, RoleGroup, RoleGroupJoinDTO>
        implements IRoleGroupService<RoleGroup> {

}