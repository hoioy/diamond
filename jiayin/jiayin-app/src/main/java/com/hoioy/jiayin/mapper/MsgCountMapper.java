package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.MsgCount;
import org.apache.ibatis.annotations.Param;

public interface MsgCountMapper extends IBaseMapper<MsgCount> {

    IPage<MsgCount> selectPage(@Param("page") Page page, @Param("msgCount") MsgCount msgCount);

    MsgCount selectByOpenid(String openid);

}
