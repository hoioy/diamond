package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 菜单DTO
 */
@Data
@NoArgsConstructor
public class MenuDTO extends BaseDTO {

    private static final long serialVersionUID = -2858011414460214454L;

    private String controllerClass;

    private String iconPath;

    private String menuDesc;

    private Integer menuIndex;

    private String menuName;

    private String menuUrl;

    private String mark;

    private String smallIconPath;

    private String authorityId;

    private String menuClassify;
}