package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseTreeService;
import com.hoioy.diamond.sys.dto.DataItemDTO;

/**
 * 数据字典Service
 */
public interface IDataItemService<D extends CommonDomain> extends IBaseTreeService<DataItemDTO, D> {

}
