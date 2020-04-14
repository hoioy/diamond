package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.TDFMybatisPageUtil;
import com.hoioy.diamond.common.util.TDFStatic;
import com.hoioy.diamond.sys.domain.UserInfo;
import com.hoioy.diamond.sys.dto.RoleUserJoinDTO;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.mapper.RoleUserMapper;
import com.hoioy.diamond.sys.mapper.UserInfoMapper;
import com.hoioy.diamond.sys.service.IDeptUserService;
import com.hoioy.diamond.sys.service.IRoleUserService;
import com.hoioy.diamond.sys.service.IUserInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo, UserInfoDTO> implements IUserInfoService<UserInfo> {

    @Autowired
    private IDeptUserService iDeptUserService;

    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Override
    public PageDTO getPage(PageDTO pageDTO) {
        UserInfo userInfo = TDFMybatisPageUtil.getBean(pageDTO, UserInfo.class);
        IPage<UserInfo> data = baseMapper.selectPage(TDFMybatisPageUtil.getPage(pageDTO), userInfo);
        return TDFMybatisPageUtil.getPageDTO(data);
    }

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

    @Override
    public void savePwd(String userId, String password) {
        findById(userId).ifPresent(userDTO -> {
            userDTO.setPassword(passwordEncoder.encode(password));
            update(userDTO);
        });
    }

    @Override
    public UserInfoDTO findByLoginName(String loginName) throws BaseException {
        UserInfo userInfo = baseMapper.findByLoginName(loginName);
        if (userInfo == null) {
            return null;
        }
        UserInfoDTO userInfoDTO = domainToDTO(userInfo);
        return userInfoDTO;
    }

    @Override
    public String update(UserInfoDTO dto) throws BaseException {
        if (StringUtils.isEmpty(dto.getId())) {
            throw new SysException("缺少ID，无法更新");
        }
        return super.update(dto);
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

        List<String> roleIds = roleUserMapper.findRoleIdsByUserIds(ids);
        List<String> deptIds = iDeptUserService.findDeptIdsByUserIds(ids);
        if (!CollectionUtils.isEmpty(roleIds)) {
            throw new SysException("有用户与角色关联，不能删除！");
        }
        if (!CollectionUtils.isEmpty(deptIds)) {
            throw new SysException("有用户与机构单位关联，不能删除！");
        }

        return super.removeByIds(ids);
    }

    @Override
    public void saveUserAvatar(String loginName, String newName, MultipartFile file) throws IOException {
        UserInfoDTO userInfoDTO = findByLoginName(loginName);
        userInfoDTO.setAvatar(newName);
        userInfoDTO.setAvatarContent(file.getBytes());
        update(userInfoDTO);
    }
}
