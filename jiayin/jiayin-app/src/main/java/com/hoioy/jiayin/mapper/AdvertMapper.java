package com.hoioy.jiayin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.jiayin.domain.Advert;
import com.hoioy.jiayin.dto.AdvertDTO;
import org.apache.ibatis.annotations.Param;

public interface AdvertMapper extends IBaseMapper<Advert> {

    /**
     * 分页
     */
    IPage<Advert> selectPage(@Param("page") Page page, @Param("advertDTO") AdvertDTO advertDTO);
}
