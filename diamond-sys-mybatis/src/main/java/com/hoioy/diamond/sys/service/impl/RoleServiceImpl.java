package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.sys.domain.Role;
import com.hoioy.diamond.sys.dto.RoleDTO;
import com.hoioy.diamond.sys.mapper.RoleMapper;
import com.hoioy.diamond.sys.service.IRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role, RoleDTO> implements IRoleService<Role> {

    @Override
    public PageDTO<RoleDTO> getPage(PageDTO<RoleDTO> pageDTO) {
        Role role = getDomainFilterFromPageDTO(pageDTO);
        IPage<Role> data = iBaseRepository.selectPage(CommonMybatisPageUtil.getPage(pageDTO), role);
        return CommonMybatisPageUtil.getPageDTO(data);
    }

    @Override
    public RoleDTO findByRoleName(String name) {
        Role role = iBaseRepository.findByRoleName(name);
        return domainToDTO(role);
    }
}
