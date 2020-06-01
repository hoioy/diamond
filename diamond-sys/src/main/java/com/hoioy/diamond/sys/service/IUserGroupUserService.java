package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseJoinService;
import com.hoioy.diamond.sys.dto.UserGroupUserJoinDTO;

import java.util.List;

public interface IUserGroupUserService<D extends CommonDomain> extends IBaseJoinService<UserGroupUserJoinDTO, D> {

    List<String> findUserIdsByGroupIds(List<String> groupIds);
    List<String> findGroupIdsByUserIds(List<String> userIds);
}
