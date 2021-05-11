package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseTreeService;
import com.hoioy.diamond.common.util.CommonCacheUtil;
import com.hoioy.diamond.sys.dto.MenuDTO;
import com.hoioy.diamond.sys.dto.MenuRouterDTO;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IMenuService<D extends CommonDomain> extends IBaseTreeService<MenuDTO, D> {
    String CacheKey_findIdsByMenuUrl = "findIdsByMenuUrl";
    Logger logger = LoggerFactory.getLogger(IMenuService.class);


    @Cacheable(value = CacheKey_findIdsByMenuUrl, key = "#menuUrl", unless = "#result==null")
    List<String> findIdsByMenuUrl(String menuUrl);


    Map findMenuAllForRouter(String loginName);

    //TODO zhaozhao 建议将此方法移植到前端实现，因为这个数据格数转换操作并不通用，只是针对vue-router菜单自己可用
    default List<MenuRouterDTO> menuListToMenuRouterList(List<MenuDTO> menuDTOList) {
        List<MenuRouterDTO> menuRouterDTOList = new ArrayList<>();
        menuDTOList.forEach(menuDTO -> {
            if (StrUtil.isBlank(menuDTO.getMenuUrl()) && StrUtil.isBlank(menuDTO.getMenuDesc())) {
                logger.info("此菜单信息不在vue前端展示：{}", menuDTO.getId());
            } else {
                MenuRouterDTO menuRouterDTO = new MenuRouterDTO();
                menuRouterDTO.setId(menuDTO.getId());
                menuRouterDTO.setParentId(menuDTO.getParentId());
                menuRouterDTO.setComponent(menuDTO.getMenuUrl());
                menuRouterDTO.setPath(menuDTO.getMenuDesc()==null?"":menuDTO.getMenuDesc());
                menuRouterDTO.setIndex(menuDTO.getMenuIndex());
                /**
                 * 用于外链形式的判断
                 */
                if (StrUtil.isNotBlank(menuDTO.getMenuName())) {
                    menuRouterDTO.setAlwaysShow(true);
                    MenuRouterDTO.meta inner = menuRouterDTO.getMenuRouterDtoMetaInstance
                            (menuDTO.getMenuIndex() + "", new ArrayList(), menuDTO.getMenuName(), menuDTO.getSmallIconPath(), false);
                    menuRouterDTO.setMeta(inner);
                    menuRouterDTO.setHidden(false);
                    menuRouterDTO.setName(menuDTO.getId());
                }
                menuRouterDTOList.add(menuRouterDTO);
            }
        });

        //菜单排序
        menuRouterDTOList.sort((o1, o2) -> o1.getIndex() - o2.getIndex());
        return menuRouterDTOList;
    }

    CommonCacheUtil getCommonCacheUtil();

    default void deleteCacheOfFindIdsByMenuUrl() {
        //有删除操作，则直接删除所有 findIdsByMenuUrl 缓存
        getCommonCacheUtil().removeByPattern(CacheKey_findIdsByMenuUrl + "*");
    }
}