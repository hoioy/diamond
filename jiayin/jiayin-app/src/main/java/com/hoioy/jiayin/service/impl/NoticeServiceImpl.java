package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.jiayin.domain.Notice;
import com.hoioy.jiayin.dto.NoticeDTO;
import com.hoioy.jiayin.mapper.NoticeMapper;
import com.hoioy.jiayin.service.INoticeService;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice, NoticeDTO> implements INoticeService<Notice> {

    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        NoticeDTO filters = (NoticeDTO) pageDTO.getFilters();
        IPage<Notice> iPage = iBaseRepository.selectPage(page, filters);
        PageDTO returnPageDTO = CommonMybatisPageUtil.getPageDTO(iPage);
        return returnPageDTO;
    }
}
