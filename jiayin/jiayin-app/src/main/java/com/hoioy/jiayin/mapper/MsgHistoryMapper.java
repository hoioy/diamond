package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.MsgHistory;
import org.apache.ibatis.annotations.Param;

public interface MsgHistoryMapper extends IBaseMapper<MsgHistory> {

    IPage<MsgHistory> selectPage(@Param("page") Page page, @Param("msgHistory") MsgHistory msgHistory);
}
