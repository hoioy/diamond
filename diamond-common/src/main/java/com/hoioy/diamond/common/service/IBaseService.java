package com.hoioy.diamond.common.service;


import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.dto.BaseDTO;
import com.hoioy.diamond.common.dto.PageDTO;

/**
 * 基础service，要求其他service继承
 */
public interface IBaseService<DTO extends BaseDTO, D extends CommonDomain> extends ICommonService<DTO, D> {
    DTO update(DTO dto);

    PageDTO<DTO> getPage(final PageDTO<DTO> pageDTO);
}
