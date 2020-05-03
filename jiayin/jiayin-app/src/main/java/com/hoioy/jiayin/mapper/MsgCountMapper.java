package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.MsgCount;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 消息次数 Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
public interface MsgCountMapper extends IBaseMapper<MsgCount> {

    /**
     *  分页
     */
    IPage<MsgCount> selectPage(@Param("page") Page page, @Param("msgCount") MsgCount msgCount);
}
