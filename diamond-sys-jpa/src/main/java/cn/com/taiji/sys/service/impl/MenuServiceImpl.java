package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.TDFBeanUtil;
import com.hoioy.diamond.sys.domain.Menu;
import com.hoioy.diamond.sys.domain.MenuRepository;
import com.hoioy.diamond.sys.domain.RoleMenuRepository;
import com.hoioy.diamond.sys.dto.MenuDTO;
import com.hoioy.diamond.sys.dto.MenuRouterDTO;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.IMenuService;
import com.hoioy.diamond.sys.service.IRoleService;
import com.hoioy.diamond.sys.service.IRoleUserService;
import com.hoioy.diamond.sys.service.IUserInfoService;
import cn.hutool.core.collection.CollUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuRepository, Menu, MenuDTO> implements IMenuService<Menu> {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RoleMenuRepository roleMenuRepository;
    @Autowired
    private IUserInfoService userService;
    @Autowired
    private IRoleUserService iRoleUserService;

    @Override
    public String save(MenuDTO dto) throws BaseException {
        if (dto != null) {
            if (StringUtils.isNotBlank(dto.getId())) {
                throw new SysException("新增操作不能传递id");
            }

            if ("external".equals(dto.getMenuClassify())) {
                Menu menuParent = new Menu();
                menuParent.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
                menuParent.setMenuName(null);
                menuParent.setMenuDesc("external");
                menuParent.setMenuUrl("Layout");
                menuParent.setMenuIndex(1);
                menuParent = this.menuRepository.saveAndFlush(menuParent);

                dto.setParentId(menuParent.getId());
            }
            if (StringUtils.isBlank(dto.getSmallIconPath())) {
                dto.setSmallIconPath("setting");
            }
        }
        return super.save(dto);
    }

    /**
     * 根据节点ID获得树
     *
     * @param rootId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MenuDTO> findMenusByParentId(String rootId) {
        List<Menu> allMenus = this.menuRepository.findByParentId(rootId);
        List<MenuDTO> list = new ArrayList();
        if (CollUtil.isNotEmpty(allMenus)) {
            for (Menu u : allMenus) {
                MenuDTO dto = new MenuDTO();
                BeanUtils.copyProperties(u, dto);
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    public List<String> findIdsByMenuUrl(String menuUrl) {
        return iBaseRepository.findIdsByMenuUrl(menuUrl);
    }

    @Override
    public List<MenuDTO> findAll() {
        return domainListToDTOList(menuRepository.findAll());
    }

    @Override
    public Map findMenuAllForRouter(String userName) {
        List<MenuRouterDTO> tree = new ArrayList<>();
        Map resultMaprouter = new HashMap();

        UserInfoDTO user = userService.findByLoginName(userName);
        List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(Arrays.asList(user.getId()));
        if (CollectionUtils.isEmpty(roleIds)) {
            resultMaprouter.put("router", tree);
            return resultMaprouter;
        }
        List<String> menuIds = roleMenuRepository.findMenuIdsByRoleIds(roleIds);
        List<MenuDTO> menuDTOs = findByIds(menuIds);

        List<MenuRouterDTO> menuRouterDTOList = menuListToMenuRouterList(menuDTOs);
        List<MenuRouterDTO> menuTree = TDFBeanUtil.getInstance().listToTree(menuRouterDTOList, null);

        resultMaprouter.put("router", menuTree);
        return resultMaprouter;

    }

    @Override
    public boolean removeById(String id) throws BaseException {
        List<Menu> children = this.menuRepository.findByParentId(id);
        if (CollUtil.isNotEmpty(children)) {
            throw new SysException("该菜单下面有子菜单，不能删除!");
        }

        return super.removeById(id);
    }
}