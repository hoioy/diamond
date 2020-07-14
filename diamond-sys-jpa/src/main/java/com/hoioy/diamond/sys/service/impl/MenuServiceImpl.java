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
import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    public MenuDTO create(MenuDTO dto) throws BaseException {
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
        return super.create(dto);
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
    public void beforeRemove(List<String> ids) {
        super.beforeRemove(ids);
        List<Menu> children = iBaseRepository.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(root.<String>get("parentId").in(ids));
            }
        });
        if (CollectionUtil.isNotEmpty(children)) {
            throw new SysException("所选数据下面含有子元素集合，不能删除！需要先删除子元素");
        }
        if (CollectionUtil.isNotEmpty(iRoleMenuService.findRoleIdsByMenuIds(ids))) {
            throw new SysException("所选菜单关联了角色，不能删除！请先删除与角色的关联");
        }
    }

    @Override
    public MenuDTO update(MenuDTO dto) throws BaseException {
        MenuDTO result = super.update(dto);
        deleteCacheOfFindIdsByMenuUrl();
        return result;
    }

    @Override
    public boolean removeById(String id) throws BaseException {
        Boolean result = super.removeById(id);
        deleteCacheOfFindIdsByMenuUrl();
        return result;
    }

    @Override
    public boolean removeByIds(List<String> ids) throws BaseException {
        Boolean result = super.removeByIds(ids);
        deleteCacheOfFindIdsByMenuUrl();
        return result;
    }

    private void deleteCacheOfFindIdsByMenuUrl() {
        //有删除操作，则直接删除所有findIdByLoginName缓存
        commonRedisUtil.removeByPattern(CacheKey_findIdsByMenuUrl + "*");
    }
}