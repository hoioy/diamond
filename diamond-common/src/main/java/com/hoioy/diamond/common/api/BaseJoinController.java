package com.hoioy.diamond.common.api;

import com.hoioy.diamond.common.dto.BaseJoinDTO;
import com.hoioy.diamond.common.dto.BatchSaveDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.service.IBaseJoinService;
import cn.hutool.core.collection.CollectionUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public abstract class BaseJoinController<S extends IBaseJoinService, DTO extends BaseJoinDTO> extends CommonController<S, DTO> {

    @ApiOperation(value = "1.新增")
    @ApiOperationSupport(order = 1)
    @PostMapping
    public ResultDTO<DTO> create(@RequestBody @Valid DTO dto) throws BaseException {
        DTO resultDTO = (DTO) iBaseService.create(dto);
        return new ResultDTO(resultDTO);
    }

    @ApiOperation(value = "2.批量新增")
    @ApiOperationSupport(order = 2)
    @PostMapping("/batchCreate")
    public ResultDTO<Boolean> batchCreate(@RequestBody @Valid List<DTO> dtos) throws BaseException {
        Boolean isSuccess = iBaseService.batchCreate(dtos);
        return new ResultDTO<>(isSuccess);
    }

    @ApiOperation(value = "3.根据DTO删除")
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/delete")
    public ResultDTO<Boolean> delete(@RequestBody DTO dto) throws BaseException {
        Boolean isSuccess = iBaseService.remove(dto);
        return new ResultDTO<>(isSuccess);
    }

    @ApiOperation(value = "4.根据多个DTO批量删除")
    @ApiOperationSupport(order = 4)
    @DeleteMapping("/batch-delete")
    public ResultDTO<Boolean> batchDelete(@RequestBody List<DTO> dtos) throws BaseException {
        Boolean isSuccess = iBaseService.batchRemove(dtos);
        return new ResultDTO<>(isSuccess);
    }

    @ApiOperation(value = "5.同时删除和新增", notes = "不用传递id属性")
    @ApiOperationSupport(order = 5)
    @PostMapping("/batch-save")
    @Transactional
    public ResultDTO<Boolean> batchSave(@RequestBody BatchSaveDTO<DTO> batchSaveDTO) throws BaseException {
        Boolean isDeleteSuccess = true;
        Boolean isCreateSuccess = true;
        if (CollectionUtil.isNotEmpty(batchSaveDTO.getDeleteDTOs())) {
            isDeleteSuccess = iBaseService.batchRemove(batchSaveDTO.getDeleteDTOs());
        }
        if (CollectionUtil.isNotEmpty(batchSaveDTO.getCreateDTOs())) {
            isCreateSuccess = iBaseService.batchCreate(batchSaveDTO.getCreateDTOs());
        }

        return new ResultDTO<>(isDeleteSuccess && isCreateSuccess);
    }

    @ApiOperation(value = "6.修改")
    @ApiOperationSupport(order = 6)
    @PutMapping
    public ResultDTO<DTO> update(@RequestBody @Valid DTO dto) throws BaseException {
        iBaseService.removeById(dto.getId());
        dto.setId(null);
        DTO resultDTO = (DTO) iBaseService.create(dto);
        return new ResultDTO(resultDTO);
    }

}
