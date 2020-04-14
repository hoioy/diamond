package com.hoioy.diamond.log.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.log.dto.WebLogsDTO;
import com.hoioy.diamond.log.exception.TDFLogException;
import com.hoioy.diamond.log.service.IWebLogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Arrays;

@ApiIgnore
@RestController
@RequestMapping("system/log")
public class WebLogsRestController extends BaseController<IWebLogsService, WebLogsDTO> {
    private static final Logger log = LoggerFactory.getLogger(WebLogsRestController.class);
    @Autowired
    private IWebLogsService webLogsService;

    @PostMapping(value = "/query-page")
    public ResultDTO queryAllPage(@RequestBody @Valid PageDTO pageDTO) {
        log.info("查询日志列表入参：{}", pageDTO);
        try {
            pageDTO = webLogsService.getPage(pageDTO);
            return new ResultDTO(pageDTO);
        } catch (Exception e) {
            log.error("查询日志列表-Exception{}:", e.getMessage());
            throw new TDFLogException("查询日志列表-Exception{}:", e.getMessage());
        }
    }

    @PostMapping(value = "/query-all")
    public ResultDTO queryAll(@RequestBody @Valid PageDTO pageDTO) {
        log.info("查询数据字典列表入参：{}", pageDTO);
        pageDTO.setPage(1);
        pageDTO.setPageSize(Integer.MAX_VALUE);
        pageDTO = webLogsService.getPage(pageDTO);
        return new ResultDTO(pageDTO);
    }

    @PostMapping(value = "/del")
    public ResultDTO del(@RequestBody String[] id) {
        webLogsService.removeByIds(Arrays.asList(id));
        return new ResultDTO();
    }
}
