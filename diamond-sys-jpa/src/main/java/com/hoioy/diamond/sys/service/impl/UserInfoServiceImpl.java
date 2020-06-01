package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonRedisUtil;
import com.hoioy.diamond.common.util.CommonStatic;
import com.hoioy.diamond.sys.domain.UserInfo;
import com.hoioy.diamond.sys.domain.UserInfoRepository;
import com.hoioy.diamond.sys.dto.RoleUserJoinDTO;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.IDeptUserService;
import com.hoioy.diamond.sys.service.IRoleUserService;
import com.hoioy.diamond.sys.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private CommonRedisUtil commonRedisUtil;

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
            userId = save(dto).getId();
        } else {
            userId = update(dto).getId();
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

    @Override
    public UserInfoDTO save(UserInfoDTO dto) throws BaseException {
        String userId = findIdByLoginName(dto.getLoginName());
        if (StringUtils.isNotEmpty(userId)) {
            throw new SysException("用户名已经存在");
        }
        // 设置默认密码
        String password = StringUtils.isEmpty(dto.getPassword()) ? CommonStatic.DEFAULT_PASSWORD : dto.getPassword();
        dto.setPassword(passwordEncoder.encode(password));
        return super.save(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('" + CommonStatic.ROLE_ID_KEY + "')")
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

        deleteCacheOfFindIdByLoginName();
        return super.removeByIds(ids);
    }

    @Override
    public UserInfoDTO update(UserInfoDTO dto) throws BaseException {
        deleteCacheOfFindIdByLoginName();
        if(StringUtils.isNotEmpty(dto.getPassword())){
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return super.update(dto);
    }

    @Override
    public boolean removeById(String id) throws BaseException {
        deleteCacheOfFindIdByLoginName();
        return super.removeById(id);
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
    public String findIdByLoginName(String loginName) {
        return this.iBaseRepository.findIdByLoginName(loginName);
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

    private void deleteCacheOfFindIdByLoginName() {
        //有删除操作，则直接删除所有findIdByLoginName缓存
        commonRedisUtil.removeByPattern(CacheKey_findIdByLoginName + "*");
    }
}