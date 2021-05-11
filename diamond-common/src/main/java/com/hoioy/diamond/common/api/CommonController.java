package com.hoioy.diamond.common.api;

import com.hoioy.diamond.common.dto.CommonDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.service.ICommonService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Validated
public abstract class CommonController<S extends ICommonService, DTO extends CommonDTO> {
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    protected S iBaseService;

    @Autowired
    protected MessageSource messageSource;

    @ApiOperation(value = "7.普通单表分页查询")
    @ApiOperationSupport(order = 7)
    @PostMapping(value = "/search")
    public ResultDTO<PageDTO<DTO>> selectByPage(@RequestBody @Valid PageDTO<DTO> pageDTO) {
        pageDTO = iBaseService.getPage(pageDTO);
        return new ResultDTO(pageDTO);
    }
}
