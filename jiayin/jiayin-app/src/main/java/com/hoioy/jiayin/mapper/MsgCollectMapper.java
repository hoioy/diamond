package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.MsgCollect;
import com.hoioy.jiayin.dto.MsgCollectDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface MsgCollectMapper extends IBaseMapper<MsgCollect> {

    IPage<Map> selectPage(@Param("page") Page page, @Param("msgCollect") MsgCollectDTO msgCollect);
}
