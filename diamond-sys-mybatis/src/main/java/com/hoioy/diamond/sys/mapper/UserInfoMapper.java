package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.diamond.sys.domain.UserInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper extends IBaseMapper<UserInfo> {

    /**
     *  分页
     */
    IPage<UserInfo> selectPage(@Param("page") Page page, @Param("userInfo") UserInfo userInfo);

    String findIdByLoginName(String loginName);

    List<UserInfo> findUsersByDeptId(String deptId);

    List<UserInfo> findUserByRoleId(String roleId);
}
