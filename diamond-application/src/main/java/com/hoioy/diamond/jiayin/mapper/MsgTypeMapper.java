package com.hoioy.diamond.jiayin.mapper;

import com.hoioy.diamond.jiayin.domain.MsgType;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 消息类型 Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
public interface MsgTypeMapper extends IBaseMapper<MsgType> {

    /**
     *  分页
     */
    IPage<MsgType> selectPage(@Param("page") Page page, @Param("msgType") MsgType msgType);
}
