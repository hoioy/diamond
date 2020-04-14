package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.TDFDomain;
import com.hoioy.diamond.common.service.IBaseJoinService;
import com.hoioy.diamond.sys.dto.UserGroupUserJoinDTO;

import java.util.List;

public interface IUserGroupUserService<D extends TDFDomain> extends IBaseJoinService<UserGroupUserJoinDTO, D> {

    List<String> findGroupIdsByUserIds(List<String> userIds);
}
