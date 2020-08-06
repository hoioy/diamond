package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseTreeService;
import com.hoioy.diamond.sys.dto.MenuDTO;
import com.hoioy.diamond.sys.dto.MenuRouterDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IMenuService<D extends CommonDomain> extends IBaseTreeService<MenuDTO, D> {
    String CacheKey_findIdsByMenuUrl = "findIdsByMenuUrl";
    Logger logger = LoggerFactory.getLogger(IMenuService.class);

//    /**
//     * 根据节点ID获得树
//     */
//    List<MenuDTO> findMenusByParentId(String rootId);

    @Cacheable(value = CacheKey_findIdsByMenuUrl, key = "#menuUrl", condition = "#result != null")
    List<String> findIdsByMenuUrl(String menuUrl);


    Map findMenuAllForRouter(String loginName);

    //TODO zhaozhao 建议将此方法移植到前端实现，因为这个数据格数转换操作并不通用，只是针对vue-router菜单自己可用
    default List<MenuRouterDTO> menuListToMenuRouterList(List<MenuDTO> menuDTOList) {
        List<MenuRouterDTO> menuRouterDTOList = new ArrayList<>();
        menuDTOList.forEach(menuDTO -> {
            if (StringUtils.isBlank(menuDTO.getMenuUrl()) && StringUtils.isBlank(menuDTO.getMenuDesc())) {
                logger.error("菜单数据错误：{}", menuDTO.getId());
            } else {
                MenuRouterDTO menuRouterDTO = new MenuRouterDTO();
                menuRouterDTO.setId(menuDTO.getId());
                menuRouterDTO.setParentId(menuDTO.getParentId());
                menuRouterDTO.setComponent(menuDTO.getMenuUrl());
                menuRouterDTO.setPath(menuDTO.getMenuDesc());
                menuRouterDTO.setIndex(menuDTO.getOrderIndex());
                /**
                 * 用于外链形式的判断
                 */
                if (StringUtils.isNotBlank(menuDTO.getMenuName())) {
                    menuRouterDTO.setAlwaysShow(true);
                    MenuRouterDTO.meta inner = menuRouterDTO.getMenuRouterDtoMetaInstance
                            (menuDTO.getOrderIndex() + "", new ArrayList(), menuDTO.getMenuName(), menuDTO.getSmallIconPath(), false);
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
}