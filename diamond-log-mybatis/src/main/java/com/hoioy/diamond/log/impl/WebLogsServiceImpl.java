package com.hoioy.diamond.log.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.log.domain.WebLogs;
import com.hoioy.diamond.log.dto.WebLogsDTO;
import com.hoioy.diamond.log.mapper.WebLogsMapper;
import com.hoioy.diamond.log.service.IWebLogsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

@Service
public class WebLogsServiceImpl extends BaseServiceImpl<WebLogsMapper, WebLogs, WebLogsDTO> implements IWebLogsService<WebLogs> {

    @Override
    public PageDTO getPage(PageDTO<WebLogsDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        WebLogs webLogs = getDomainFilterFromPageDTO(pageDTO);
        IPage<WebLogs> webLogsIPage = iBaseRepository.getPage(page, webLogs);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(webLogsIPage,WebLogsDTO.class,pageDTO);
        return resultPage;
    }
}
