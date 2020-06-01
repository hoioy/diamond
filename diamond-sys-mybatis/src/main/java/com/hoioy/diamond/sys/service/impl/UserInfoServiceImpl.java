package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.common.util.CommonRedisUtil;
import com.hoioy.diamond.common.util.CommonStatic;
import com.hoioy.diamond.sys.domain.UserInfo;
import com.hoioy.diamond.sys.dto.RoleUserJoinDTO;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
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

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo, UserInfoDTO> implements IUserInfoService<UserInfo> {

    @Autowired
    private IDeptUserService iDeptUserService;

    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CommonRedisUtil commonRedisUtil;

    @Override
    public PageDTO<UserInfoDTO> getPage(PageDTO<UserInfoDTO> pageDTO) {
        UserInfo userInfo = getDomainFilterFromPageDTO(pageDTO);
        IPage<UserInfo> data = iBaseRepository.selectPage(CommonMybatisPageUtil.getPage(pageDTO), userInfo);
        return CommonMybatisPageUtil.getPageDTO(data);
    }

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
    public String findIdByLoginName(String loginName) throws BaseException {
        return iBaseRepository.findIdByLoginName(loginName);
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
    public UserInfoDTO save(UserInfoDTO dto) throws BaseException {
        // 设置默认密码
        dto.setPassword(passwordEncoder.encode(CommonStatic.DEFAULT_PASSWORD));
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
    public boolean removeById(String id) throws BaseException {
        deleteCacheOfFindIdByLoginName();
        return super.removeById(id);
    }

    @Override
    public void saveUserAvatar(String loginName, String newName, MultipartFile file) throws IOException {
        String id = findIdByLoginName(loginName);
        UserInfoDTO userInfoDTO = findById(id).get();
        userInfoDTO.setAvatar(newName);
        userInfoDTO.setAvatarContent(file.getBytes());
        update(userInfoDTO);
    }

    private void deleteCacheOfFindIdByLoginName() {
        //有删除操作，则直接删除所有findIdByLoginName缓存
        commonRedisUtil.removeByPattern(CacheKey_findIdByLoginName + "*");
    }
}
