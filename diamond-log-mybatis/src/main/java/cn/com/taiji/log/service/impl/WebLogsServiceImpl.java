package com.hoioy.diamond.log.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.TDFMybatisPageUtil;
import com.hoioy.diamond.common.util.TDFSecurityUtils;
import com.hoioy.diamond.common.util.WebSiteUtil;
import com.hoioy.diamond.log.annotation.OperationLogAnno;
import com.hoioy.diamond.log.domain.WebLogs;
import com.hoioy.diamond.log.dto.WebLogsDTO;
import com.hoioy.diamond.log.mapper.WebLogsMapper;
import com.hoioy.diamond.log.service.IWebLogsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;

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
        Page page = TDFMybatisPageUtil.getPage(pageDTO);
        WebLogs webLogs = TDFMybatisPageUtil.getBean(pageDTO, WebLogs.class);
        IPage<WebLogs> webLogsIPage = null;
        try {
            webLogsIPage = baseMapper.getPage(page, webLogs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageDTO resultPage = TDFMybatisPageUtil.getPageDTO(webLogsIPage);
        return resultPage;
    }
}
