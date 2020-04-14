package com.hoioy.diamond.log.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.log.domain.WebLogs;
import com.hoioy.diamond.log.domain.WebLogsRepository;
import com.hoioy.diamond.log.dto.WebLogsDTO;
import com.hoioy.diamond.log.service.IWebLogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 类名称： WebLogsService 类描述： 创建人： 创建时间：Mar 22, 2018 6:47:16 PM
 */
@Service
public class WebLogsServiceImpl extends BaseServiceImpl<WebLogsRepository, WebLogs, WebLogsDTO> implements IWebLogsService<WebLogs> {

}
