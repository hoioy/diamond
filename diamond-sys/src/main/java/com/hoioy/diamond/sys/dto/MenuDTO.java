package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseTreeDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 菜单DTO
 */
@Data
@NoArgsConstructor
public class MenuDTO extends BaseTreeDTO {

    private static final long serialVersionUID = -28594460214454L;
    @ApiModelProperty(value = "暂时没用")
    private String controllerClass;

    @ApiModelProperty(value = "图标路径")
    private String iconPath;

    @ApiModelProperty(value = "菜单描述")
    private String menuDesc;

    @ApiModelProperty(value = "菜单排序号")
    private Integer menuIndex;

    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty(value = "菜单名称")
    private String menuName;


    @ApiModelProperty(value = "菜单路径")
    private String menuUrl;


    @ApiModelProperty(value = "菜单备注")
    private String mark;


    @ApiModelProperty(value = "小图标途径")
    private String smallIconPath;

    @ApiModelProperty(value = "按钮权限ID")
    private String authorityId;
    @ApiModelProperty(value = "菜单类型")
    private String menuClassify;
}