package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseJoinServiceImpl;
import com.hoioy.diamond.sys.domain.DeptUser;
import com.hoioy.diamond.sys.domain.DeptUserRepository;
import com.hoioy.diamond.sys.dto.DeptUserJoinDTO;
import com.hoioy.diamond.sys.service.IDeptUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptUserServiceImpl extends BaseJoinServiceImpl<DeptUserRepository, DeptUser, DeptUserJoinDTO> implements IDeptUserService<DeptUser> {

    @Override
    public List<String> findDeptIdsByUserIds(List<String> userIds) {
        return iBaseRepository.findDeptIdsByUserIds(userIds);
    }

    @Override
    public List<String> findUserIdsByDeptIds(List<String> deptIds) {
        return iBaseRepository.findUserIdsByDeptIds(deptIds);
    }
}