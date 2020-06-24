package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.jiayin.domain.MsgCount;
import com.hoioy.jiayin.dto.MsgCountDTO;
import com.hoioy.jiayin.mapper.MsgCountMapper;
import com.hoioy.jiayin.service.IMsgCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsgCountServiceImpl extends BaseServiceImpl<MsgCountMapper, MsgCount, MsgCountDTO> implements IMsgCountService<MsgCount> {

    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        MsgCount msgCount = getDomainFilterFromPageDTO(pageDTO);
        IPage<MsgCount> messageIPage = iBaseRepository.selectPage(page, msgCount);
        PageDTO returnPageDTO = CommonMybatisPageUtil.getPageDTO(messageIPage);
        return returnPageDTO;
    }

}
