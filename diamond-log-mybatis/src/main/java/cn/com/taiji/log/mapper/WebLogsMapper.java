package com.hoioy.diamond.log.mapper;

import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.diamond.log.domain.WebLogs;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-01
 */
public interface WebLogsMapper extends IBaseMapper<WebLogs> {

    /**
     *  分页
     */
    IPage<WebLogs> getPage(@Param("page") Page page, @Param("webLogs") WebLogs webLogs);

    List<WebLogs> selectAll(WebLogs webLogs);
}
