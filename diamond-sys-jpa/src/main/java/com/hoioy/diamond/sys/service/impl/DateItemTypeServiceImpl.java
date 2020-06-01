package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.sys.domain.DataItemType;
import com.hoioy.diamond.sys.domain.DataItemTypeRepository;
import com.hoioy.diamond.sys.dto.DataItemTypeDTO;
import com.hoioy.diamond.sys.service.IDataItemTypeService;
import org.springframework.stereotype.Service;

@Service
public class DateItemTypeServiceImpl extends BaseServiceImpl<DataItemTypeRepository, DataItemType, DataItemTypeDTO> implements IDataItemTypeService<DataItemType> {

}
