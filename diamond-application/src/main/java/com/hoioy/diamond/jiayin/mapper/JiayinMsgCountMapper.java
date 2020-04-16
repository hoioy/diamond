package com.hoioy.diamond.jiayin.mapper;

import com.hoioy.diamond.jiayin.domain.JiayinMsgCount;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 消息次数 Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-16
 */
public interface JiayinMsgCountMapper extends IBaseMapper<JiayinMsgCount> {

    /**
     *  分页
     */
    IPage<JiayinMsgCount> selectPage(@Param("page") Page page, @Param("jiayinMsgCount") JiayinMsgCount jiayinMsgCount);
}
