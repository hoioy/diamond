package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.diamond.sys.domain.DataItem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
public interface DataItemMapper extends IBaseMapper<DataItem> {

    /**
     * 分页
     */
    IPage<DataItem> selectPage(@Param("page") Page page, @Param("dataItem") DataItem dataItem);

    DataItem findByCode(String code);

    List<DataItem> selectByParentId(String parentId);
}
