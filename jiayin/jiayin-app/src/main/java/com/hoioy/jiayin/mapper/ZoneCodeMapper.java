package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseTreeMapper;
import com.hoioy.jiayin.domain.MsgType;
import com.hoioy.jiayin.domain.ZoneCode;
import org.apache.ibatis.annotations.Param;

public interface ZoneCodeMapper extends IBaseTreeMapper<ZoneCode> {

    IPage<ZoneCode> selectPage(@Param("page") Page page, @Param("zoneCode") ZoneCode zoneCode);
}
