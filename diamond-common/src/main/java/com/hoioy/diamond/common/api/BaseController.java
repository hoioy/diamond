package com.hoioy.diamond.common.api;

import com.hoioy.diamond.common.dto.BaseDTO;
import com.hoioy.diamond.common.dto.CommonDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.service.IBaseService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public abstract class BaseController<S extends IBaseService, DTO extends BaseDTO> {
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    protected S iBaseService;

    @Valid
    @ApiOperation(value = "001.新增")
    @ApiOperationSupport(order = 1)
    @PostMapping
    public ResultDTO<DTO> create(@RequestBody DTO dto) throws BaseException {
        DTO resultDTO = (DTO) iBaseService.create(dto);
        return new ResultDTO(resultDTO);
    }

    @ApiOperation(value = "002.批量新增")
    @ApiOperationSupport(order = 2)
    @PostMapping("/batchCreate")
    public ResultDTO batchCreate(@RequestBody List<DTO> dtos) throws BaseException {
        iBaseService.batchCreate(dtos);
        return new ResultDTO();
    }

    @Valid
    @ApiOperation(value = "003.修改")
    @ApiOperationSupport(order = 3)
    @PutMapping
    public ResultDTO<String> update(@RequestBody DTO dto) throws BaseException {
        DTO resultDTO = (DTO) iBaseService.update(dto);
        return new ResultDTO(resultDTO);
    }

    @ApiOperation(value = "004.根据id删除")
    @ApiOperationSupport(order = 4)
    @DeleteMapping("/{id}")
    public ResultDTO<Boolean> deleteById(@PathVariable("id") String id) throws BaseException {
        Boolean isSuccess = iBaseService.removeById(id);
        return new ResultDTO<Boolean>(isSuccess);
    }

    @ApiOperation(value = "005.根据多个id批量删除")
    @ApiOperationSupport(order = 5)
    @DeleteMapping("/deleteByIds")
    public ResultDTO<Boolean> deleteByIds(@RequestBody List<String> ids) throws BaseException {
        Boolean isSuccess = iBaseService.removeByIds(ids);
        return new ResultDTO<Boolean>(isSuccess);
    }

    @ApiOperation(value = "006.根据id查询")
    @ApiOperationSupport(order = 6)
    @GetMapping("/{id}")
    public ResultDTO<DTO> selectById(@PathVariable("id") String id) throws BaseException {
        CommonDTO byId = iBaseService.findById(id);
        return new ResultDTO(byId);
    }

    @Valid
    @ApiOperation(value = "007.普通单表分页查询")
    @PostMapping(value = "/search")
    public ResultDTO<PageDTO<DTO>> selectByPage(@RequestBody PageDTO<DTO> pageDTO) {
        pageDTO = iBaseService.getPage(pageDTO);
        return new ResultDTO(pageDTO);
    }
}
