package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseTreeDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 后台菜单到前台的路由
 */
@Data
@NoArgsConstructor
public class MenuRouterDTO extends BaseTreeDTO implements Comparable<MenuRouterDTO> {
    private static final long serialVersionUID = 8531051872528629002L;
    @ApiModelProperty(value = "请求路径")
    private String path;
    @ApiModelProperty(value = "组件")
    private String component;
    /**
     * 预留
     */
    // private String redirect;
    private Boolean hidden;
    @ApiModelProperty(value = "是否显示")
    private Boolean alwaysShow;
    @ApiModelProperty(value = "路由名称")
    private String name;
    @ApiModelProperty(value = "菜单数据")
    private meta meta;
    @ApiModelProperty(value = "排序子弹")
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
    public class meta implements Serializable {
        private static final long serialVersionUID = 8632051832528629005L;

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