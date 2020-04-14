package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 后台菜单到前台的路由
 */
@Data
@NoArgsConstructor
public class MenuRouterDTO extends BaseDTO implements Comparable<MenuRouterDTO> {
    private static final long serialVersionUID = 8531051872528629002L;

    private String path;
    private String component;
    /**
     * 预留
     */
    // private String redirect;
    private Boolean hidden;
    private Boolean alwaysShow;
    private String name;
    private meta meta;
    private Integer index;

    public meta getMenuRouterDtoMetaInstance(String index, List role, String title, String icon, Boolean cacheable) {
        return new meta(index, role, title, icon, cacheable);
    }

    @Override
    public int compareTo(MenuRouterDTO o) {
        return this.getIndex() - o.getIndex();
    }

    @Data
    @NoArgsConstructor
    public class meta {
        private static final long serialVersionUID = 8632051832528629002L;

        public meta(String index, List role, String title, String icon, Boolean cacheable) {
            this.setTitle(title);
            this.setRoles(role);
            this.setIndex(index);
            this.setIcon(icon);
            this.setCacheAble(cacheable);
        }

        private String index;
        private List roles;
        private String title;
        private String icon;
        private Boolean cacheAble;
    }
}