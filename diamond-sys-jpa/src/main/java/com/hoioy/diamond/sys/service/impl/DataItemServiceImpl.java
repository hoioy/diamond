package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseTreeServiceImpl;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.sys.domain.DataItem;
import com.hoioy.diamond.sys.domain.DataItemRepository;
import com.hoioy.diamond.sys.dto.DataItemDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.IDataItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Description: 数据字典Service
 */
@Service
public class DataItemServiceImpl extends BaseTreeServiceImpl<DataItemRepository, DataItem, DataItemDTO> implements IDataItemService<DataItem> {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(String id) throws BaseException {
        if (StringUtils.isEmpty(id)) {
            throw new SysException("id不能为空");
        }
        List<DataItem> children = iBaseRepository.findByParentId(id);
        if (!CollectionUtils.isEmpty(children)) {
            throw new SysException("该条记录下面含有子元素集合，不能删除！");
        }
        // 标记为已删除-0,未删除-1
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(List<String> ids) throws BaseException {
        if (CollectionUtils.isEmpty(ids)) {
            throw new SysException("参数不能为null");
        }

        for (String id : ids) {
            removeById(id);
        }
        return true;
    }
}
