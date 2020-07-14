package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.diamond.sys.domain.UserInfo;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper extends IBaseMapper<UserInfo> {

    IPage<Map> getUserOnlyByRoleIdOrDeptIdPage(@Param("page") Page page, @Param("userInfoDTO") UserInfoDTO userInfoDTO);
    /**
     *  分页
     */
    IPage<UserInfo> selectPage(@Param("page") Page page, @Param("userInfo") UserInfo userInfo);

    String findIdByLoginName(String loginName);

    List<UserInfo> findUsersByDeptId(String deptId);

    List<UserInfo> findUserByRoleId(String roleId);
}
