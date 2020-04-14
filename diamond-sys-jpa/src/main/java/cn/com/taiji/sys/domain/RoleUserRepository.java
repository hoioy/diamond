package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.IBaseJoinRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色用户表Repository
 */
@Repository
public interface RoleUserRepository extends IBaseJoinRepository<RoleUser, String> {

    @Query("select k.userId from RoleUser k where k.roleId in (:roleIds)")
    List<String> findUserIdsByRoleIds(@Param("roleIds") List<String> roleIds);

    @Query("select k.roleId from RoleUser k where k.userId in (:userIds)")
    List<String> findRoleIdsByUserIds(@Param("userIds") List<String> userIds);
}
