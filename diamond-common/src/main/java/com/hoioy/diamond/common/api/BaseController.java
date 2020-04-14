package com.hoioy.diamond.common.api;

import com.hoioy.diamond.common.dto.BaseDTO;
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
import java.util.Optional;

public abstract class BaseController<S extends IBaseService, DTO extends BaseDTO> {
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    protected S iBaseService;

    @Valid
    @ApiOperation(value = "001.新增")
    @ApiOperationSupport(order = 1)
    @PostMapping("/save")
    public ResultDTO<String> save(@RequestBody DTO dto) throws BaseException {
        String id = iBaseService.save(dto);
        return new ResultDTO<String>(id);
    }

    @ApiOperation(value = "002.批量新增")
    @ApiOperationSupport(order = 2)
    @PostMapping("/batchSave")
    public ResultDTO batchSave(@RequestBody List<DTO> dtos) throws BaseException {
        iBaseService.batchSave(dtos);
        return new ResultDTO();
    }

    @Valid
    @ApiOperation(value = "003.修改")
    @ApiOperationSupport(order = 3)
    @PutMapping("/update")
    public ResultDTO<String> update(@RequestBody DTO dto) throws BaseException {
        String id = iBaseService.update(dto);
        return new ResultDTO<String>(id);
    }

    @ApiOperation(value = "004.根据id删除")
    @ApiOperationSupport(order = 4)
    @DeleteMapping("/deleteById")
    public ResultDTO<Boolean> deleteById(@RequestParam("id") String id) throws BaseException {
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
    @GetMapping("/selectById")
    public ResultDTO<DTO> selectById(@RequestParam String id) throws BaseException {
        Optional<DTO> byId = iBaseService.findById(id);
        return new ResultDTO<DTO>(byId.get());
    }

    @Valid
    @ApiOperation(value = "普通单表分页查询")
    @PostMapping(value = "/selectByPage")
    public ResultDTO selectByPage(@RequestBody PageDTO pageDTO) {
        pageDTO = iBaseService.getPage(pageDTO);
        return new ResultDTO(pageDTO);
    }
}
