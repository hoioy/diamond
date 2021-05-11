package com.hoioy.diamond.sys.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.service.CommonSecurityService;
import com.hoioy.diamond.common.util.CommonCacheUtil;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
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
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private CommonCacheUtil commonCacheUtil;

    @Override
    public PageDTO<UserInfoDTO> getUserOnlyByRoleIdOrDeptIdPage(PageDTO<UserInfoDTO> pageDTO) {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        UserInfoDTO userInfoDTO = pageDTO.getFilters();
        IPage<Map> pageList = iBaseRepository.getUserOnlyByRoleIdOrDeptIdPage(page, userInfoDTO);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(pageList, UserInfoDTO.class, pageDTO);
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
    public UserInfoDTO findByPhoneNum(String phoneNum) {
        UserInfo u = iBaseRepository.findByPhoneNum(phoneNum);
        return domainToDTO(u);
    }

    @Override
    public Boolean checkUserExist(String loginName) {
        String s = iBaseRepository.checkUserExist(loginName);
        if (StrUtil.isNotBlank(s)) {
            return true;
        }
        return false;
    }

    @Override
    public PageDTO<UserInfoDTO> getPage(PageDTO<UserInfoDTO> pageDTO) {
        UserInfo userInfo = getDomainFilterFromPageDTO(pageDTO);
        IPage<UserInfo> data = iBaseRepository.selectPage(CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO), userInfo);
        pageDTO = CommonMybatisPageUtil.getInstance().iPageToPageDTO(data, UserInfoDTO.class, pageDTO);
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
            if (CollectionUtil.isNotEmpty(roleIds)) {
                List<RoleUserJoinDTO> roleUserJoinDTOS = new ArrayList<>();
                for (String roleId : roleIds) {
                    RoleUserJoinDTO roleUserJoinDTO = new RoleUserJoinDTO(roleId, userId);
                    roleUserJoinDTOS.add(roleUserJoinDTO);
                }

                iRoleUserService.batchCreate(roleUserJoinDTOS);
            }
        } else {
            userId = update(dto).getId();
        }

        return userId;
    }

    @Override
    public String findIdByLoginName(String loginName) throws BaseException {
        return iBaseRepository.findIdByLoginName(loginName);
    }

    @Override
    public String findIdByPhoneNum(String phoneNum) {
        return iBaseRepository.findIdByPhoneNum(phoneNum);
    }

    @Override
    public String findIdByEmail(String email) {
        return iBaseRepository.findIdByEmail(email);
    }

    @Override
    public UserInfoDTO beforeCreate(UserInfoDTO dto) throws BaseException {
        dto = super.beforeCreate(dto);
        if (StrUtil.isNotBlank(dto.getLoginName())) {
            String userId = findIdByLoginName(dto.getLoginName());
            if (StrUtil.isNotBlank(userId)) {
                throw new SysException("用户名已经存在");
            }
        }
        if (StrUtil.isNotBlank(dto.getPhoneNum())) {
            String phoneNum = findIdByPhoneNum(dto.getPhoneNum());
            if (StrUtil.isNotBlank(phoneNum)) {
                throw new SysException("手机号已经被绑定");
            }
        }
        if (StrUtil.isNotBlank(dto.getEmail())) {
            String email = findIdByEmail(dto.getEmail());
            if (StrUtil.isNotBlank(email)) {
                throw new SysException("邮箱已经被绑定");
            }
        }
        // 设置默认密码
        String password = StrUtil.isBlank(dto.getPassword()) ? CommonStatic.DEFAULT_PASSWORD : dto.getPassword();
        dto.setPassword(CommonSecurityService.instance.encodePassword(password));
        dto.setState("1");
        return dto;
    }

    @Override
    public Boolean beforeBatchRemove(List<String> ids) {
        super.beforeBatchRemove(ids);
        List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(ids);
        List<String> deptIds = iDeptUserService.findDeptIdsByUserIds(ids);
        if (CollectionUtil.isNotEmpty(roleIds)) {
            throw new SysException(messageSource.getMessage("exception.containRole", null, LocaleContextHolder.getLocale()));
        }
        if (CollectionUtil.isNotEmpty(deptIds)) {
            throw new SysException(messageSource.getMessage("exception.containDept", null, LocaleContextHolder.getLocale()));
        }
        return true;
    }

    @Override
    //    @PreAuthorize("hasAuthority('" + CommonStatic.ROLE_ID_KEY + "')")
    //TODO 注释掉 PreAuthorize 待完善鉴权系统来替代此方法
    public Boolean afterRemove(String id) {
        deleteCacheOfFindIdByLoginName();
        return super.afterRemove(id);
    }

    @Override
    public Boolean afterBatchRemove(List<String> ids) {
        deleteCacheOfFindIdByLoginName();
        return super.afterBatchRemove(ids);
    }

    @Override
    public UserInfoDTO beforeUpdate(UserInfoDTO dto) throws BaseException {
        dto = super.beforeUpdate(dto);
        deleteCacheOfFindIdByLoginName();
        if (StrUtil.isNotBlank(dto.getPassword())) {
            dto.setPassword(CommonSecurityService.instance.encodePassword(dto.getPassword()));
        }
        return dto;
    }

    private void deleteCacheOfFindIdByLoginName() {
        //有删除操作，则直接删除所有findIdByLoginName缓存
        commonCacheUtil.removeByPattern(CacheKey_findIdByLoginName + "*");
    }
}
