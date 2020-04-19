package com.hoioy.diamond.jiayin.mapper;

import com.hoioy.diamond.jiayin.domain.MsgCollect;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 消息收藏 Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
public interface MsgCollectMapper extends IBaseMapper<MsgCollect> {

    /**
     *  分页
     */
    IPage<MsgCollect> selectPage(@Param("page") Page page, @Param("msgCollect") MsgCollect msgCollect);
}
