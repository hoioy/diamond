package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseTreeServiceImpl;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonRedisUtil;
import com.hoioy.diamond.sys.domain.Menu;
import com.hoioy.diamond.sys.domain.MenuRepository;
import com.hoioy.diamond.sys.dto.MenuDTO;
import com.hoioy.diamond.sys.dto.MenuRouterDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.IMenuService;
import com.hoioy.diamond.sys.service.IRoleMenuService;
import com.hoioy.diamond.sys.service.IRoleUserService;
import com.hoioy.diamond.sys.service.IUserInfoService;
import cn.hutool.core.collection.CollUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuServiceImpl extends BaseTreeServiceImpl<MenuRepository, Menu, MenuDTO> implements IMenuService<Menu> {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private IUserInfoService userService;
    @Autowired
    private IRoleUserService iRoleUserService;
    @Autowired
    private IRoleMenuService iRoleMenuService;
    @Autowired
    private CommonRedisUtil commonRedisUtil;

    @Override
    public MenuDTO save(MenuDTO dto) throws BaseException {
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

    @Override
    public List<String> findIdsByMenuUrl(String menuUrl) {
        if (StringUtils.isNotEmpty(menuUrl)) {
            return iBaseRepository.findIdsByMenuUrl(menuUrl);
        }
        return null;
    }

    @Override
    public Map findMenuAllForRouter(String loginName) {
        List<MenuRouterDTO> tree = new ArrayList<>();
        Map resultMaprouter = new HashMap();

        String userId = userService.findIdByLoginName(loginName);
        List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(Arrays.asList(userId));
        if (CollectionUtils.isEmpty(roleIds)) {
            resultMaprouter.put("router", tree);
            return resultMaprouter;
        }
        List<String> menuIds = iRoleMenuService.findMenuIdsByRoleIds(roleIds);
        List<MenuDTO> menuDTOs = findByIds(menuIds);

        List<MenuRouterDTO> menuRouterDTOList = menuListToMenuRouterList(menuDTOs);
        List<MenuRouterDTO> menuTree = listToTree(menuRouterDTOList, null);

        resultMaprouter.put("router", menuTree);
        return resultMaprouter;

    }

    @Override
    public boolean removeById(String id) throws BaseException {
        List<Menu> children = this.menuRepository.findByParentId(id);
        if (CollUtil.isNotEmpty(children)) {
            throw new SysException("该菜单下面有子菜单，不能删除!");
        }

        deleteCacheOfFindIdsByMenuUrl();
        return super.removeById(id);
    }

    @Override
    public MenuDTO update(MenuDTO dto) throws BaseException {
        deleteCacheOfFindIdsByMenuUrl();
        return super.update(dto);
    }

    @Override
    public boolean removeByIds(List<String> ids) throws BaseException {
        deleteCacheOfFindIdsByMenuUrl();
        return super.removeByIds(ids);
    }

    private void deleteCacheOfFindIdsByMenuUrl() {
        //有删除操作，则直接删除所有findIdByLoginName缓存
        commonRedisUtil.removeByPattern(CacheKey_findIdsByMenuUrl + "*");
    }
}