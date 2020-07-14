package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 用户Service
 */
public interface IUserInfoService<D extends CommonDomain> extends IBaseService<UserInfoDTO, D> {
    String CacheKey_findIdByLoginName = "findIdByLoginName";

    /**
     * 带有角色的用户保存(为community单独的方法)
     */
    String saveUserWithRoles(UserInfoDTO dto, List<String> roleIds);

    /**
     * 根据用户登录名查询用户
     */
    @Cacheable(value = CacheKey_findIdByLoginName, key = "#loginName", sync = true, condition = "#result != null")
    String findIdByLoginName(String loginName);

    PageDTO<UserInfoDTO> getUserOnlyByRoleIdOrDeptIdPage(PageDTO<UserInfoDTO> pageDTO) ;

    UserInfoDTO findWithPasswordById(String id);

}