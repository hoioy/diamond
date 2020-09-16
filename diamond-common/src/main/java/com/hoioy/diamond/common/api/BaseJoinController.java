package com.hoioy.diamond.common.api;

import com.hoioy.diamond.common.dto.BaseJoinDTO;
import com.hoioy.diamond.common.dto.BatchSaveDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.service.IBaseJoinService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public abstract class BaseJoinController<S extends IBaseJoinService, DTO extends BaseJoinDTO> {
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    protected S iBaseService;

    @Autowired
    protected MessageSource messageSource;

    @Valid
    @ApiOperation(value = "001.新增")
    @ApiOperationSupport(order = 1)
    @PostMapping
    public ResultDTO<DTO> create(@RequestBody DTO dto) throws BaseException {
        DTO resultDTO = (DTO) iBaseService.create(dto);
        return new ResultDTO(resultDTO);
    }

    @Valid
    @ApiOperation(value = "002.批量新增")
    @ApiOperationSupport(order = 2)
    @PostMapping("/batchCreate")
    public ResultDTO<Boolean> batchCreate(@RequestBody List<DTO> dtos) throws BaseException {
        Boolean isSuccess = iBaseService.batchCreate(dtos);
        return new ResultDTO<Boolean>(isSuccess);
    }

    @ApiOperation(value = "003.根据DTO删除")
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/delete")
    public ResultDTO<Boolean> delete(@RequestBody DTO dto) throws BaseException {
        Boolean isSuccess = iBaseService.remove(dto);
        return new ResultDTO<Boolean>(isSuccess);
    }

    @ApiOperation(value = "004.根据多个DTO批量删除")
    @ApiOperationSupport(order = 4)
    @DeleteMapping("/batch-delete")
    public ResultDTO<Boolean> batchDelete(@RequestBody List<DTO> dtos) throws BaseException {
        Boolean isSuccess = iBaseService.batchRemove(dtos);
        return new ResultDTO<Boolean>(isSuccess);
    }

    @ApiOperation(value = "005.同时删除和新增", notes = "不用传递id属性")
    @ApiOperationSupport(order = 5)
    @PostMapping("/batch-save")
    @Transactional
    public ResultDTO<Boolean> batchDelete(@RequestBody BatchSaveDTO<DTO> batchSaveDTO) throws BaseException {
        Boolean isDeleteSuccess = iBaseService.batchRemove(batchSaveDTO.getDeleteDTOs());
        Boolean isCreateSuccess = iBaseService.batchCreate(batchSaveDTO.getCreateDTOs());
        return new ResultDTO<Boolean>(isDeleteSuccess && isCreateSuccess);
    }

    @Valid
    @ApiOperation(value = "006.修改")
    @ApiOperationSupport(order = 3)
    @PutMapping
    public ResultDTO<DTO> update(@RequestBody DTO dto) throws BaseException {
        Boolean removeResult = iBaseService.removeById(dto.getId());
        DTO resultDTO = (DTO) iBaseService.create(dto);
        return new ResultDTO(resultDTO);
    }

}
