package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.sys.domain.DataItemType;
import com.hoioy.diamond.sys.dto.DataItemTypeDTO;
import com.hoioy.diamond.sys.mapper.DataItemTypeMapper;
import com.hoioy.diamond.sys.service.IDataItemTypeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

@Service
public class DataItemTypeServiceImpl extends BaseServiceImpl<DataItemTypeMapper, DataItemType, DataItemTypeDTO> implements IDataItemTypeService<DataItemType> {

    @Override
    public PageDTO<DataItemTypeDTO> getPage(PageDTO<DataItemTypeDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        DataItemType dataItem = getDomainFilterFromPageDTO(pageDTO);
        IPage<DataItemType> dataItemIPage = iBaseRepository.selectPage(page, dataItem);
        PageDTO resultPage = CommonMybatisPageUtil.getPageDTO(dataItemIPage);
        return resultPage;
    }
}
