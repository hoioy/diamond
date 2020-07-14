package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseTreeServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.common.util.CommonRedisUtil;
import com.hoioy.diamond.sys.domain.Menu;
import com.hoioy.diamond.sys.dto.MenuDTO;
import com.hoioy.diamond.sys.dto.MenuRouterDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.mapper.MenuMapper;
import com.hoioy.diamond.sys.service.IMenuService;
import com.hoioy.diamond.sys.service.IRoleMenuService;
import com.hoioy.diamond.sys.service.IRoleUserService;
import com.hoioy.diamond.sys.service.IUserInfoService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuServiceImpl extends BaseTreeServiceImpl<MenuMapper, Menu, MenuDTO> implements IMenuService<Menu> {

    @Autowired
    private IUserInfoService iUserInfoService;

    @Autowired
    private IRoleMenuService iRoleMenuService;

    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private CommonRedisUtil commonRedisUtil;

    @Override
    public List<String> findIdsByMenuUrl(String menuUrl) {
        return iBaseRepository.findIdsByMenuUrl(menuUrl);
    }

    @Override
    public PageDTO<MenuDTO> getPage(PageDTO<MenuDTO> pageDTO) {
        Menu menu = getDomainFilterFromPageDTO(pageDTO);
        IPage<Menu> data = iBaseRepository.selectPage(CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO), menu);
        return CommonMybatisPageUtil.getInstance().iPageToPageDTO(data,MenuDTO.class);
    }

    @Override
    public Map findMenuAllForRouter(String loginName) {
        List<MenuRouterDTO> tree = new ArrayList<>();
        Map resultMaprouter = new HashMap();
        //TODO 优化zhaozhao，直接根据用户id查询菜单
        String userId = iUserInfoService.findIdByLoginName(loginName);
        if (StringUtils.isBlank(userId)) {
            throw new SysException("用户不存在");
        }
        List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(Arrays.asList(userId));
        if (CollectionUtils.isEmpty(roleIds)) {
            resultMaprouter.put("router", tree);
            return resultMaprouter;
        }
        List<String> menuIds = iRoleMenuService.findMenuIdsByRoleIds(roleIds);
        List<MenuDTO> menuDTOs = findByIds(menuIds);

        List<MenuRouterDTO> menuRouterDTOList = menuListToMenuRouterList(menuDTOs);
        List<MenuRouterDTO> menuTree = this.listToTree(menuRouterDTOList, null);

        resultMaprouter.put("router", menuTree);
        return resultMaprouter;
    }

    @Override
    public void beforeRemove(List<String> ids) {
        super.beforeRemove(ids);
        QueryWrapper<Menu> ew = new QueryWrapper<>();
        ew.in("parent_id", ids);
        List<Menu> children = iBaseRepository.selectList(ew);
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
        //有删除操作，则直接删除所有 findIdsByMenuUrl 缓存
        commonRedisUtil.removeByPattern(CacheKey_findIdsByMenuUrl + "*");
    }
}
