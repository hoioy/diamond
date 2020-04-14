package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseJoinMapper;
import com.hoioy.diamond.sys.domain.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
public interface RoleMenuMapper extends IBaseJoinMapper<RoleMenu> {
    List<String> findMenuIdsByRoleIds(@Param("roleIds") List<String> roleIds);

    List<String> findRoleIdsByMenuIds(@Param("menuIds") List<String> menuIds);
}
