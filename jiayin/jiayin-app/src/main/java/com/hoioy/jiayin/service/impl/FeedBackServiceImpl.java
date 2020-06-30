package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.jiayin.domain.FeedBack;
import com.hoioy.jiayin.dto.FeedBackDTO;
import com.hoioy.jiayin.mapper.FeedBackMapper;
import com.hoioy.jiayin.service.IFeedBackService;
import org.springframework.stereotype.Service;

@Service
public class FeedBackServiceImpl extends BaseServiceImpl<FeedBackMapper, FeedBack, FeedBackDTO> implements IFeedBackService<FeedBack> {

    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        FeedBackDTO filters = (FeedBackDTO) pageDTO.getFilters();
        IPage<FeedBack> iPage = iBaseRepository.selectPage(page, filters);
        PageDTO returnPageDTO = CommonMybatisPageUtil.getPageDTO(iPage);
        return returnPageDTO;
    }
}
