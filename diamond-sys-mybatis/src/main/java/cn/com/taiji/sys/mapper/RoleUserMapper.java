package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseJoinMapper;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.diamond.sys.domain.RoleUser;
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
public interface RoleUserMapper extends IBaseJoinMapper<RoleUser> {

    List<String> findRoleIdsByUserIds(@Param("userIds") List<String> userIds);

    List<String> findUserIdsByRoleIds(@Param("roleIds") List<String> roleIds);
}
