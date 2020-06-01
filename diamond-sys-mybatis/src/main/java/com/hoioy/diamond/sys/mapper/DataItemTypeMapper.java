package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.diamond.sys.domain.DataItemType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface DataItemTypeMapper extends IBaseMapper<DataItemType> {

    /**
     * 分页
     */
    IPage<DataItemType> selectPage(@Param("page") Page page, @Param("dataItemType") DataItemType dataItemType);
}
