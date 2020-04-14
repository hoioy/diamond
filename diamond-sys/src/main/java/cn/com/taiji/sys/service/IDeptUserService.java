package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.TDFDomain;
import com.hoioy.diamond.common.service.IBaseJoinService;
import com.hoioy.diamond.sys.dto.DeptUserJoinDTO;

import java.util.List;

public interface IDeptUserService<D extends TDFDomain> extends IBaseJoinService<DeptUserJoinDTO, D> {

    List<String> findUserIdsByDeptIds(List<String> deptIds);

    List<String> findDeptIdsByUserIds(List<String> userIds);
}
