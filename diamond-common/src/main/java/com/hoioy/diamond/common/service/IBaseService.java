package com.hoioy.diamond.common.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.dto.BaseDTO;
import cn.hutool.core.util.IdUtil;

/**
 * 业务表基础service
 */
public interface IBaseService<DTO extends BaseDTO, D extends CommonDomain> extends IBaseCommonService<DTO, D> {
    @Override
    default DTO beforeCreate(DTO dto) {
        dto = IBaseCommonService.super.beforeCreate(dto);
        dto.setId(IdUtil.simpleUUID());
        // 初始化
        dto.setFlag(1);
        return dto;
    }
}
