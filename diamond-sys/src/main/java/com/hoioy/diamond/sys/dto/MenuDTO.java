package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseTreeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单DTO
 */
@Data
@NoArgsConstructor
public class MenuDTO extends BaseTreeDTO {

    private static final long serialVersionUID = -28594460214454L;

    private String controllerClass;

    private String iconPath;

    private String menuDesc;

    private String menuName;

    private String menuUrl;

    private String mark;

    private String smallIconPath;

    private String authorityId;

    private String menuClassify;
}