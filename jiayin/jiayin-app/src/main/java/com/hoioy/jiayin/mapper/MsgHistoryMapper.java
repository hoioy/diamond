package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.MsgHistory;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 观看历史 Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
public interface MsgHistoryMapper extends IBaseMapper<MsgHistory> {

    /**
     *  分页
     */
    IPage<MsgHistory> selectPage(@Param("page") Page page, @Param("msgHistory") MsgHistory msgHistory);
}
