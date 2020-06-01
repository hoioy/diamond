package com.hoioy.diamond.log.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.log.dto.WebLogsDTO;
import com.hoioy.diamond.log.service.IWebLogsService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"日志查看"})
@RestController
@RequestMapping("sys/log")
public class WebLogsRestController extends BaseController<IWebLogsService, WebLogsDTO> {

}
