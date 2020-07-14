package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonJpaPageUtil;
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
import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public PageDTO<UserInfoDTO> getUserOnlyByRoleIdOrDeptIdPage(PageDTO<UserInfoDTO> pageDTO) {
        PageRequest pageable = CommonJpaPageUtil.getInstance().toPageRequest(pageDTO);
        //获取查询参数
        Page<UserInfoDTO> pageList = iBaseRepository.getUserOnlyByRoleIdOrDeptIdPage(pageDTO.getFilters(), pageable);
        pageDTO.setTotal(pageList.getTotalElements());
        pageDTO.setList(pageList.getContent());
        if (CollectionUtil.isNotEmpty(pageDTO.getList())) {
            pageDTO.getList().forEach(item -> {
                ((UserInfoDTO) item).setPassword(null);
            });
        }
        return pageDTO;
    }

    @Override
    public PageDTO<UserInfoDTO> getPage(PageDTO<UserInfoDTO> pageDTO) {
        pageDTO = super.getPage(pageDTO);
        if (CollectionUtil.isNotEmpty(pageDTO.getList())) {
            pageDTO.getList().forEach(item -> {
                ((UserInfoDTO) item).setPassword(null);
            });
        }
        return pageDTO;
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
    public UserInfoDTO create(UserInfoDTO dto) throws BaseException {
        String userId = findIdByLoginName(dto.getLoginName());
        if (StringUtils.isNotEmpty(userId)) {
            throw new SysException("用户名已经存在");
        }
        // 设置默认密码
        String password = StringUtils.isBlank(dto.getPassword()) ? CommonStatic.DEFAULT_PASSWORD : dto.getPassword();
        dto.setPassword(passwordEncoder.encode(password));
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
        deleteCacheOfFindIdByLoginName();
        return super.removeByIds(ids);
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
    public boolean removeById(String id) throws BaseException {
        Boolean r = super.removeById(id);
        deleteCacheOfFindIdByLoginName();
        return r;
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

    private void deleteCacheOfFindIdByLoginName() {
        //有删除操作，则直接删除所有findIdByLoginName缓存
        commonRedisUtil.removeByPattern(CacheKey_findIdByLoginName + "*");
    }
}