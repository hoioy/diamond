package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.TDFDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.sys.dto.ParameterInfoDTO;

import java.util.List;

/**
 * 参数管理
 */
public interface IParameterInfoService<D extends TDFDomain> extends IBaseService<ParameterInfoDTO, D> {

    List<String> findIdsByParameterKeys(List<String> parameterKeys);
}
