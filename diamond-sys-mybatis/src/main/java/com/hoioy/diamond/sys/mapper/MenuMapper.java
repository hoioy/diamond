package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.diamond.sys.domain.Menu;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
public interface MenuMapper extends IBaseMapper<Menu> {

    /**
     *  分页
     */
    IPage<Menu> selectPage(@Param("page") Page page, @Param("menu") Menu menu);

    List<Menu> findByRoleId(String roleId);

    List<String> findIdsByMenuUrl(String menuUrl);

    List<Menu> findMenusByRoleIds(@Param("roleIds") List<String> roleIds);

    List<Menu> findChildrenByParentId(String parentId);
}
