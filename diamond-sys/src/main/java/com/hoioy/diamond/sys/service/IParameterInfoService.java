package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.DiamondDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.sys.dto.ParameterInfoDTO;

import java.util.List;

/**
 * 参数管理
 */
public interface IParameterInfoService<D extends DiamondDomain> extends IBaseService<ParameterInfoDTO, D> {

    List<String> findIdsByParameterKeys(List<String> parameterKeys);
}
