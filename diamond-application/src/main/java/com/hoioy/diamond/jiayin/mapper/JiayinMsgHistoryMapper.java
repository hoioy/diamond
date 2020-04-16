package com.hoioy.diamond.jiayin.mapper;

import com.hoioy.diamond.jiayin.domain.JiayinMsgHistory;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 观看历史 Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-16
 */
public interface JiayinMsgHistoryMapper extends IBaseMapper<JiayinMsgHistory> {

    /**
     *  分页
     */
    IPage<JiayinMsgHistory> selectPage(@Param("page") Page page, @Param("jiayinMsgHistory") JiayinMsgHistory jiayinMsgHistory);
}
