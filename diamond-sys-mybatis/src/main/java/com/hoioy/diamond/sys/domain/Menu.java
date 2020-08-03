package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseTreeDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("menu")
public class Menu extends BaseTreeDomain {

    private static final long serialVersionUID = 1L;

    private String authorityId;

    private String controllerClass;

    private String iconPath;

    private String mark;

    private String menuClassify;

    private String menuDesc;

    private String menuName;

    private String menuUrl;

    private String smallIconPath;

}
