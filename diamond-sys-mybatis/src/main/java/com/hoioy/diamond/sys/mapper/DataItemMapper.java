package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseTreeMapper;
import com.hoioy.diamond.sys.domain.DataItem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DataItemMapper extends IBaseTreeMapper<DataItem> {

    /**
     * 分页
     */
    IPage<Map> selectPage(@Param("page") Page page, @Param("dataItem") DataItem dataItem);

    List<DataItem> findByDataItemTypeId(@Param("dataItemTypeIds") List<String> dataItemTypeIds);
}
