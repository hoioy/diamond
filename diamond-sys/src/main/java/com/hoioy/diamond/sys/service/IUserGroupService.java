package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.DiamondDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.sys.dto.UserGroupDTO;

import java.util.List;

/**
 * 用户组Group Service
 */
public interface IUserGroupService<D extends DiamondDomain> extends IBaseService<UserGroupDTO, D> {

    List<String> findUserIdsByGroupIds(List<String> groupIds);
}
