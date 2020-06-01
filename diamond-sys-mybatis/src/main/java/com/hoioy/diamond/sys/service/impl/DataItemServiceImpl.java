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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataItemServiceImpl extends BaseTreeServiceImpl<DataItemMapper, DataItem, DataItemDTO> implements IDataItemService<DataItem> {

    @Override
    public PageDTO<DataItemDTO> getPage(PageDTO<DataItemDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        DataItem dataItem = getDomainFilterFromPageDTO(pageDTO);
        IPage<DataItem> dataItemIPage = iBaseRepository.selectPage(page, dataItem);
        PageDTO resultPage = CommonMybatisPageUtil.getPageDTO(dataItemIPage);
        return resultPage;
    }

    @Override
    public boolean removeById(String id) throws BaseException {
        if (StrUtil.isBlank(id)) {
            throw new SysException("id不能为空");
        }
        List<DataItemDTO> byParentId = this.findByParentId(id);
        if (CollUtil.isNotEmpty(byParentId)) {
            throw new SysException("该条记录下面含有子元素集合，不能删除！");
        }
        return super.removeById(id);

    }

    @Override
    public DataItemDTO save(DataItemDTO dto) throws BaseException {
        DataItem byCode = iBaseRepository.findByCode(dto.getCode());
        if (byCode != null) {
            throw new SysException("该code已存在");
        }
        return super.save(dto);
    }

    @Override
    public DataItemDTO update(DataItemDTO dto) throws BaseException {
        DataItem byCode = iBaseRepository.findByCode(dto.getCode());
        if (byCode != null) {
            throw new SysException("该code已存在");
        }
        return super.update(dto);
    }
}
