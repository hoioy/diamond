package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.TDFMybatisPageUtil;
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

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
@Service
public class DataItemServiceImpl extends BaseServiceImpl<DataItemMapper, DataItem, DataItemDTO> implements IDataItemService<DataItem> {

    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = TDFMybatisPageUtil.getPage(pageDTO);
        DataItem dataItem = TDFMybatisPageUtil.getBean(pageDTO, DataItem.class);
        IPage<DataItem> dataItemIPage = baseMapper.selectPage(page, dataItem);
        PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(dataItemIPage);
        return resultPage;
    }

    @Override
    public boolean removeById(String id) throws BaseException {
        if (StrUtil.isBlank(id)) {
            throw new SysException("id不能为空");
        }
        List<DataItem> dataItems = this.baseMapper.selectByParentId(id);
        if (CollUtil.isNotEmpty(dataItems)) {
            throw new SysException("该条记录下面含有子元素集合，不能删除！");
        }
        return super.removeById(id);

    }

    @Override
    public String save(DataItemDTO dto) throws BaseException {
        DataItem byCode = baseMapper.findByCode(dto.getCode());
        if (byCode != null) {
            throw new SysException("该code已存在");
        }
        return super.save(dto);
    }

    @Override
    public String update(DataItemDTO dto) throws BaseException {
        DataItem byCode = baseMapper.findByCode(dto.getCode());
        if (byCode != null) {
            throw new SysException("该code已存在");
        }
        return super.update(dto);
    }
}
