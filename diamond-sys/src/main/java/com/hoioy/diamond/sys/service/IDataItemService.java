package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.service.IBaseTreeService;
import com.hoioy.diamond.sys.dto.DataItemDTO;

import java.util.List;

/**
 * 数据字典Service
 */
public interface IDataItemService<D extends CommonDomain> extends IBaseTreeService<DataItemDTO, D> {
	
    PageDTO<DataItemDTO> findDataItemByTypePageable(final PageDTO<DataItemDTO> pageDTO);

    List<DataItemDTO> findByDataItemTypeId(List<String> dataItemTypeIds);
}
