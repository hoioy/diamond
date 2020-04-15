package com.hoioy.diamond.log.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.DiamondMybatisPageUtil;
import com.hoioy.diamond.log.domain.WebLogs;
import com.hoioy.diamond.log.dto.WebLogsDTO;
import com.hoioy.diamond.log.mapper.WebLogsMapper;
import com.hoioy.diamond.log.service.IWebLogsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-01
 */
@Service
public class WebLogsServiceImpl extends BaseServiceImpl<WebLogsMapper, WebLogs, WebLogsDTO> implements IWebLogsService<WebLogs> {

    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = DiamondMybatisPageUtil.getPage(pageDTO);
        WebLogs webLogs = DiamondMybatisPageUtil.getBean(pageDTO, WebLogs.class);
        IPage<WebLogs> webLogsIPage = null;
        try {
            webLogsIPage = baseMapper.getPage(page, webLogs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageDTO resultPage = DiamondMybatisPageUtil.getPageDTO(webLogsIPage);
        return resultPage;
    }
}
