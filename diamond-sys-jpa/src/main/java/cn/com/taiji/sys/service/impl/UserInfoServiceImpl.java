package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.TDFStatic;
import com.hoioy.diamond.sys.domain.UserInfo;
import com.hoioy.diamond.sys.domain.UserInfoRepository;
import com.hoioy.diamond.sys.dto.RoleUserJoinDTO;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.IDeptUserService;
import com.hoioy.diamond.sys.service.IRoleUserService;
import com.hoioy.diamond.sys.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户Service
 */
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoRepository, UserInfo, UserInfoDTO> implements IUserInfoService<UserInfo> {
    @Autowired
    private IDeptUserService iDeptUserService;
    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @throws @author dourl
     * @Description: 带有角色的用户保存(为community单独的方法)
     * void
     * @date 2018年3月22日
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveUserWithRoles(UserInfoDTO dto, List<String> roleIds) {
        if (dto == null) {
            return null;
        }
        String userId = null;
        if (dto.getId() == null) {
            userId = save(dto);
        } else {
            userId = update(dto);
        }

        if (!CollectionUtils.isEmpty(roleIds)) {
            List<RoleUserJoinDTO> roleUserJoinDTOS = new ArrayList<>();
            for (String roleId : roleIds) {
                RoleUserJoinDTO roleUserJoinDTO = new RoleUserJoinDTO(roleId, userId);
                roleUserJoinDTOS.add(roleUserJoinDTO);
            }

            Boolean success = iRoleUserService.batchSave(roleUserJoinDTOS);
        }
        return userId;
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param password
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void savePwd(String userId, String password) {
        iBaseRepository.findById(userId).ifPresent(user -> {
            user.setPassword(passwordEncoder.encode(password));
            update(domainToDTO(user));
        });
    }

    @Override
    public String save(UserInfoDTO dto) throws BaseException {
        // 设置默认密码
        dto.setPassword(passwordEncoder.encode(TDFStatic.DEFAULT_PASSWORD));
        return super.save(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('" + TDFStatic.ROLE_ID_KEY + "')")
    public boolean removeByIds(List<String> ids) throws BaseException {
        if (CollectionUtils.isEmpty(ids)) {
            return true;
        }

        List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(ids);
        List<String> deptIds = iDeptUserService.findDeptIdsByUserIds(ids);
        if (!CollectionUtils.isEmpty(roleIds)) {
            throw new SysException("有用户与角色关联，不能删除！");
        }
        if (!CollectionUtils.isEmpty(deptIds)) {
            throw new SysException("有用户与机构单位关联，不能删除！");
        }

        return super.removeByIds(ids);
    }

    /**
     * @param loginName
     * @return UserInfoDTO
     * @throws @author dourl
     * @Description: 根据用户登录名查询用户
     * @date 2017年5月23日
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserInfoDTO findByLoginName(String loginName) {
        UserInfo userInfo = this.iBaseRepository.findByLoginName(loginName);
        return (userInfo != null) ? domainToDTO(userInfo) : null;
    }

    /**
     * 保存用户头像
     *
     * @param newName
     * @param file
     * @throws IOException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUserAvatar(String loginName, String newName, MultipartFile file) throws IOException {
        byte[] avatarContent = null;
        avatarContent = file.getBytes();
        this.iBaseRepository.updateAvatar(loginName, newName, avatarContent);
    }
}