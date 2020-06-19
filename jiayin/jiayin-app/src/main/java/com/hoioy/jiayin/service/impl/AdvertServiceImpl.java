package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.jiayin.domain.Advert;
import com.hoioy.jiayin.dto.AdvertDTO;
import com.hoioy.jiayin.mapper.AdvertMapper;
import com.hoioy.jiayin.service.IAdvertService;
import org.springframework.stereotype.Service;

@Service
public class AdvertServiceImpl extends BaseServiceImpl<AdvertMapper, Advert, AdvertDTO> implements IAdvertService<Advert> {

    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        AdvertDTO filters = (AdvertDTO) pageDTO.getFilters();
        IPage<Advert> iPage = iBaseRepository.selectPage(page, filters);
        PageDTO returnPageDTO = CommonMybatisPageUtil.getPageDTO(iPage);
        return returnPageDTO;
    }
}
