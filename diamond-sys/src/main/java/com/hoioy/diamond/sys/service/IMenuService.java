package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.DiamondDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.sys.dto.MenuDTO;
import com.hoioy.diamond.sys.dto.MenuRouterDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IMenuService<D extends DiamondDomain> extends IBaseService<MenuDTO, D> {
    Logger logger = LoggerFactory.getLogger(IMenuService.class);

    /**
     * 根据节点ID获得树
     */
    List<MenuDTO> findMenusByParentId(String rootId);

    List<String> findIdsByMenuUrl(String menuUrl);

    List<MenuDTO> findAll();

    Map findMenuAllForRouter(String userName);

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
                menuRouterDTO.setIndex(menuDTO.getMenuIndex());
                /**
                 * 用于外链形式的判断
                 */
                if (StringUtils.isNotBlank(menuDTO.getMenuName())) {
                    menuRouterDTO.setAlwaysShow(true);
                    MenuRouterDTO.meta inner = menuRouterDTO.getMenuRouterDtoMetaInstance
                            (menuDTO.getMenuIndex()+"", new ArrayList(), menuDTO.getMenuName(), menuDTO.getSmallIconPath(), false);
                    menuRouterDTO.setMeta(inner);
                    menuRouterDTO.setHidden(false);
                    menuRouterDTO.setName(menuDTO.getId());
                }
                menuRouterDTOList.add(menuRouterDTO);
            }
        });
        return menuRouterDTOList;
    }
}