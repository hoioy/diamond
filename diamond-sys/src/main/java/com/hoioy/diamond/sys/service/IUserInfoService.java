package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.TDFDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 用户Service
 */
public interface IUserInfoService<D extends TDFDomain> extends IBaseService<UserInfoDTO, D> {
    /**
     * 带有角色的用户保存(为community单独的方法)
     */
    String saveUserWithRoles(UserInfoDTO dto,  List<String> roleIds);

    /**
     * 修改密码
     */
    void savePwd(String userId, String password);

    /**
     * 根据用户登录名查询用户
     */
    UserInfoDTO findByLoginName(String loginName);


    /**
     * 保存用户头像
     */
    void saveUserAvatar(String loginName, String newName, MultipartFile file) throws IOException;
}