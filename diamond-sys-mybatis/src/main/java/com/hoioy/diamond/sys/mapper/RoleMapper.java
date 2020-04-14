package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.diamond.sys.domain.Role;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
public interface RoleMapper extends IBaseMapper<Role> {

    /**
     *  分页
     */
    IPage<Role> selectPage(@Param("page") Page page, @Param("role") Role role);

    Role findByRoleName(String roleName);
}
