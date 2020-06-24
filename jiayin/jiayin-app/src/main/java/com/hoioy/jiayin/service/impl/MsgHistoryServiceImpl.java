package com.hoioy.jiayin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.jiayin.domain.MsgHistory;
import com.hoioy.jiayin.dto.MsgHistoryDTO;
import com.hoioy.jiayin.mapper.MsgHistoryMapper;
import com.hoioy.jiayin.service.IMsgHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsgHistoryServiceImpl extends BaseServiceImpl<MsgHistoryMapper, MsgHistory, MsgHistoryDTO> implements IMsgHistoryService<MsgHistory> {

    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getPage(pageDTO);
        MsgHistory msgHistory = getDomainFilterFromPageDTO(pageDTO);
        IPage<MsgHistory> messageIPage = iBaseRepository.selectPage(page, msgHistory);
        PageDTO returnPageDTO = CommonMybatisPageUtil.getPageDTO(messageIPage);
        return returnPageDTO;
    }

}
