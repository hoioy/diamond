package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseTreeServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.sys.domain.DataItem;
import com.hoioy.diamond.sys.dto.DataItemDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.mapper.DataItemMapper;
import com.hoioy.diamond.sys.service.IDataItemService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.context.i18n.LocaleContextHolder;
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
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(dataItemIPage, DataItemDTO.class);
        return resultPage;
    }

    @Override
    public void beforeRemove(List<String> ids) {
        super.beforeRemove(ids);
        QueryWrapper<DataItem> ew = new QueryWrapper<>();
        ew.in("parent_id", ids);
        List<DataItem> children = iBaseRepository.selectList(ew);
        if (CollectionUtil.isNotEmpty(children)) {
            throw new SysException(messageSource.getMessage("exception.hasChild", null,  LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public PageDTO<DataItemDTO> findDataItemByTypePageable(PageDTO<DataItemDTO> pageDTO) {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        DataItem dataItem = getDomainFilterFromPageDTO(pageDTO);
        IPage<Map> dataItemIPage = iBaseRepository.selectPage(page, dataItem);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(dataItemIPage,DataItemDTO.class);
        return resultPage;
    }

}
