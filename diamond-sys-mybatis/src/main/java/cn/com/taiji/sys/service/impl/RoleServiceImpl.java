package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.util.TDFMybatisPageUtil;
import com.hoioy.diamond.sys.domain.Role;
import com.hoioy.diamond.sys.dto.RoleDTO;
import com.hoioy.diamond.sys.mapper.MenuMapper;
import com.hoioy.diamond.sys.mapper.RoleMapper;
import com.hoioy.diamond.sys.mapper.RoleMenuMapper;
import com.hoioy.diamond.sys.mapper.RoleUserMapper;
import com.hoioy.diamond.sys.service.IMenuService;
import com.hoioy.diamond.sys.service.IRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role, RoleDTO> implements IRoleService<Role> {

    @Override
    public PageDTO getPage(PageDTO pageDTO) {
        Role role = TDFMybatisPageUtil.getBean(pageDTO, Role.class);
        IPage<Role> data = baseMapper.selectPage(TDFMybatisPageUtil.getPage(pageDTO), role);
        return TDFMybatisPageUtil.getPageDTO(data);
    }

    @Override
    public List<RoleDTO> findAll() {
        List<Role> roles = super.list();
        return domainListToDTOList(roles);
    }

    @Override
    public RoleDTO findByRoleName(String name) {
        Role role = baseMapper.findByRoleName(name);
        return domainToDTO(role);
    }
}
