package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.FeedBack;
import com.hoioy.jiayin.dto.FeedBackDTO;
import org.apache.ibatis.annotations.Param;

public interface FeedBackMapper extends IBaseMapper<FeedBack> {

    IPage<FeedBack> selectPage(@Param("page") Page page, @Param("feedBackDTO") FeedBackDTO feedBackDTO);
}
