package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.MsgCollect;
import com.hoioy.jiayin.dto.MsgCollectDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

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
    IPage<Map> selectPage(@Param("page") Page page, @Param("msgCollect") MsgCollectDTO msgCollect);
}
