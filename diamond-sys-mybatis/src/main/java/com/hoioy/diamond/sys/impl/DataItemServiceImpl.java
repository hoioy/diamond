package com.hoioy.diamond.sys.impl;

import com.hoioy.diamond.common.base.BaseTreeServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.sys.domain.DataItem;
import com.hoioy.diamond.sys.dto.DataItemDTO;
import com.hoioy.diamond.sys.mapper.DataItemMapper;
import com.hoioy.diamond.sys.service.IDataItemService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataItemServiceImpl extends BaseTreeServiceImpl<DataItemMapper, DataItem, DataItemDTO> implements IDataItemService<DataItem> {

    @Override
    public PageDTO<DataItemDTO> getPage(PageDTO<DataItemDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        DataItem dataItem = getDomainFilterFromPageDTO(pageDTO);
        IPage<Map> dataItemIPage = iBaseRepository.selectPage(page, dataItem);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(dataItemIPage, DataItemDTO.class,pageDTO);
        return resultPage;
    }

    @Override
    public PageDTO<DataItemDTO> findDataItemByTypePageable(PageDTO<DataItemDTO> pageDTO) {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        DataItem dataItem = getDomainFilterFromPageDTO(pageDTO);
        IPage<Map> dataItemIPage = iBaseRepository.selectPage(page, dataItem);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(dataItemIPage,DataItemDTO.class,pageDTO);
        return resultPage;
    }

    @Override
    public List<DataItemDTO> findByDataItemTypeId(List<String> dataItemTypeIds) {
        List<DataItem> dataItems = iBaseRepository.findByDataItemTypeId(dataItemTypeIds);
        List<DataItemDTO> dataItemDTOS = domainListToDTOList(dataItems);
        return dataItemDTOS;
    }
}
