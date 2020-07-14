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
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public PageDTO<UserInfoDTO> getUserOnlyByRoleIdOrDeptIdPage(PageDTO<UserInfoDTO> pageDTO) {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        UserInfoDTO userInfoDTO = pageDTO.getFilters();
        IPage<Map> pageList = iBaseRepository.getUserOnlyByRoleIdOrDeptIdPage(page, userInfoDTO);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(pageList, UserInfoDTO.class);
        return resultPage;
    }

    @Override
    public UserInfoDTO findById(String id) throws BaseException {
        UserInfoDTO userInfoDTO = super.findById(id);
        if (userInfoDTO != null) {
            userInfoDTO.setPassword(null);
        }
        return userInfoDTO;
    }

    @Override
    public UserInfoDTO findWithPasswordById(String id) throws BaseException {
        return super.findById(id);
    }

    @Override
    public PageDTO<UserInfoDTO> getPage(PageDTO<UserInfoDTO> pageDTO) {
        UserInfo userInfo = getDomainFilterFromPageDTO(pageDTO);
        IPage<UserInfo> data = iBaseRepository.selectPage(CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO), userInfo);
        pageDTO = CommonMybatisPageUtil.getInstance().iPageToPageDTO(data,UserInfoDTO.class);
        return pageDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveUserWithRoles(UserInfoDTO dto, List<String> roleIds) {
        if (dto == null) {
            return null;
        }
        String userId = null;
        if (dto.getId() == null) {
            userId = create(dto).getId();
        } else {
            userId = update(dto).getId();
        }

        if (CollectionUtil.isNotEmpty(roleIds)) {
            List<RoleUserJoinDTO> roleUserJoinDTOS = new ArrayList<>();
            for (String roleId : roleIds) {
                RoleUserJoinDTO roleUserJoinDTO = new RoleUserJoinDTO(roleId, userId);
                roleUserJoinDTOS.add(roleUserJoinDTO);
            }

            Boolean success = iRoleUserService.batchCreate(roleUserJoinDTOS);
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
        if (StringUtils.isNotEmpty(dto.getPassword())) {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return super.update(dto);
    }

    @Override
    public UserInfoDTO create(UserInfoDTO dto) throws BaseException {
        // 设置默认密码
        dto.setPassword(passwordEncoder.encode(CommonStatic.DEFAULT_PASSWORD));
        return super.create(dto);
    }

    @Override
    public void beforeRemove(List<String> ids) {
        super.beforeRemove(ids);
        List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(ids);
        List<String> deptIds = iDeptUserService.findDeptIdsByUserIds(ids);
        if (CollectionUtil.isNotEmpty(roleIds)) {
            throw new SysException("有用户与角色关联，不能删除！");
        }
        if (CollectionUtil.isNotEmpty(deptIds)) {
            throw new SysException("有用户与机构单位关联，不能删除！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('" + CommonStatic.ROLE_ID_KEY + "')")
    public boolean removeByIds(List<String> ids) throws BaseException {
        boolean r = super.removeByIds(ids);
        deleteCacheOfFindIdByLoginName();
        return r;
    }

    @Override
    public boolean removeById(String id) throws BaseException {
        boolean r = super.removeById(id);
        deleteCacheOfFindIdByLoginName();
        return r;
    }

    private void deleteCacheOfFindIdByLoginName() {
        //有删除操作，则直接删除所有findIdByLoginName缓存
        commonRedisUtil.removeByPattern(CacheKey_findIdByLoginName + "*");
    }
}
