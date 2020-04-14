package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.IBaseJoinRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单角色表Repository
 */
@Repository
public interface RoleMenuRepository extends IBaseJoinRepository<RoleMenu, String> {

    @Query("select k.menuId from RoleMenu k where k.roleId in (:roleIds)")
    List<String> findMenuIdsByRoleIds(@Param("roleIds") List<String> roleIds);


    @Query("select k.roleId from RoleMenu k where k.menuId in (:menuIds)")
    List<String> findRoleIdsByMenuIds(@Param("menuIds") List<String> menuIds);
}
