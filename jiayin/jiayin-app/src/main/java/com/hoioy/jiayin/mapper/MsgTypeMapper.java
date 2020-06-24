package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseTreeMapper;
import com.hoioy.jiayin.domain.MsgType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MsgTypeMapper extends IBaseTreeMapper<MsgType> {

    IPage<MsgType> selectPage(@Param("page") Page page, @Param("msgType") MsgType msgType);
}
