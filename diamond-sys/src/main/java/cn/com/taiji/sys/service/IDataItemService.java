package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.TDFDomain;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.sys.dto.DataItemDTO;

/**
 * 数据字典Service
 */
public interface IDataItemService<D extends TDFDomain> extends IBaseService<DataItemDTO, D> {

}
