package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.TDFDomain;
import com.hoioy.diamond.common.service.IBaseJoinService;
import com.hoioy.diamond.sys.dto.RoleUserJoinDTO;

import java.util.List;

public interface IRoleUserService<D extends TDFDomain> extends IBaseJoinService<RoleUserJoinDTO, D> {

    /**
     * 保存角色用户的分配
     */
    void saveRoleUsers(String[] userIds, String preUserIds, String roleId);

    List<String> findUserIdsByRoleIds(List<String> roleIds);

    List<String> findRoleIdsByUserIds(List<String> userIds);
}
