package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.DiamondBeanUtil;
import com.hoioy.diamond.common.util.DiamondMybatisPageUtil;
import com.hoioy.diamond.sys.domain.Menu;
import com.hoioy.diamond.sys.domain.UserInfo;
import com.hoioy.diamond.sys.dto.MenuDTO;
import com.hoioy.diamond.sys.dto.MenuRouterDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.mapper.MenuMapper;
import com.hoioy.diamond.sys.mapper.RoleMenuMapper;
import com.hoioy.diamond.sys.mapper.RoleUserMapper;
import com.hoioy.diamond.sys.mapper.UserInfoMapper;
import com.hoioy.diamond.sys.service.*;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu, MenuDTO> implements IMenuService<Menu> {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Override
    public List<String> findIdsByMenuUrl(String menuUrl) {
        return baseMapper.findIdsByMenuUrl(menuUrl);
    }

    @Override
    public List<MenuDTO> findAll() {
        List<Menu> list = super.list();
        List<MenuDTO> menuDTOS = domainListToDTOList(list);
        return menuDTOS;
    }

    @Override
    public List<MenuDTO> findMenusByParentId(String rootId) {
        return null;
    }

    @Override
    public PageDTO getPage(PageDTO pageDTO) {
        Menu menu = DiamondMybatisPageUtil.getBean(pageDTO, Menu.class);
        IPage<Menu> data = baseMapper.selectPage(DiamondMybatisPageUtil.getPage(pageDTO), menu);
        return DiamondMybatisPageUtil.getPageDTO(data);
    }

    @Override
    public boolean removeById(String id) throws BaseException {
        List<String> roleIdsByMenuIds = roleMenuMapper.findRoleIdsByMenuIds(Arrays.asList(id));
        if (CollUtil.isNotEmpty(roleIdsByMenuIds)) {
            throw new SysException("该菜单以被分配角色");
        }
        List<Menu> menus = baseMapper.findChildrenByParentId(id);
        if (CollUtil.isNotEmpty(menus)) {
            throw new SysException("请先删除子菜单");
        }
        return super.removeById(id);
    }

    @Override
    public Map findMenuAllForRouter(String userName) {
        List<MenuRouterDTO> tree = new ArrayList<>();
        Map resultMaprouter = new HashMap();
        //TODO 优化zhaozhao，直接根据用户id查询菜单
        UserInfo user = userInfoMapper.findByLoginName(userName);
        if (user == null) {
            throw new SysException("用户不存在");
        }
        List<String> roleIds = roleUserMapper.findRoleIdsByUserIds(Arrays.asList(user.getId()));
        if (CollectionUtils.isEmpty(roleIds)) {
            resultMaprouter.put("router", tree);
            return resultMaprouter;
        }
        List<String> menuIds = roleMenuMapper.findMenuIdsByRoleIds(roleIds);
        List<MenuDTO> menuDTOs = findByIds(menuIds);

        List<MenuRouterDTO> menuRouterDTOList = menuListToMenuRouterList(menuDTOs);
        List<MenuRouterDTO> menuTree = DiamondBeanUtil.getInstance().listToTree(menuRouterDTOList, null);

        resultMaprouter.put("router", menuTree);
        return resultMaprouter;
    }
}
