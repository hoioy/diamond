package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.DiamondDomain;
import com.hoioy.diamond.common.service.IBaseJoinService;
import com.hoioy.diamond.sys.dto.UserGroupUserJoinDTO;

import java.util.List;

public interface IUserGroupUserService<D extends DiamondDomain> extends IBaseJoinService<UserGroupUserJoinDTO, D> {

    List<String> findGroupIdsByUserIds(List<String> userIds);
}
