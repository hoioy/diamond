package com.hoioy.diamond.sys.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.exception.CommonException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.sys.domain.DataItem;
import com.hoioy.diamond.sys.domain.DataItemType;
import com.hoioy.diamond.sys.dto.DataItemDTO;
import com.hoioy.diamond.sys.dto.DataItemTypeDTO;
import com.hoioy.diamond.sys.mapper.DataItemTypeMapper;
import com.hoioy.diamond.sys.service.IDataItemService;
import com.hoioy.diamond.sys.service.IDataItemTypeService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataItemTypeServiceImpl extends BaseServiceImpl<DataItemTypeMapper, DataItemType, DataItemTypeDTO> implements IDataItemTypeService<DataItemType> {

    @Autowired
    private IDataItemService<DataItem> iDataItemService;
    @Override
    public PageDTO<DataItemTypeDTO> getPage(PageDTO<DataItemTypeDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        DataItemType dataItem = getDomainFilterFromPageDTO(pageDTO);
        IPage<DataItemType> dataItemIPage = iBaseRepository.selectPage(page, dataItem);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(dataItemIPage, DataItemTypeDTO.class,pageDTO);
        return resultPage;
    }

    @Override
     public Boolean beforeBatchRemove(List<String> ids) {
        super.beforeBatchRemove(ids);
        List<DataItemDTO> byDataItemTypeId = iDataItemService.findByDataItemTypeId(ids);
        if (CollectionUtil.isNotEmpty(byDataItemTypeId)) {
            throw new CommonException("字典类型正被使用");
        }
        return true;
    }
}
