package com.hoioy.diamond.log.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.log.domain.WebLogs;
import com.hoioy.diamond.log.domain.WebLogsRepository;
import com.hoioy.diamond.log.dto.WebLogsDTO;
import com.hoioy.diamond.log.service.IWebLogsService;
import org.springframework.stereotype.Service;

@Service
public class WebLogsServiceImpl extends BaseServiceImpl<WebLogsRepository, WebLogs, WebLogsDTO> implements IWebLogsService<WebLogs> {

}
