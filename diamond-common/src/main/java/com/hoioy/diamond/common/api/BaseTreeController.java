package com.hoioy.diamond.common.api;

import com.hoioy.diamond.common.dto.BaseTreeDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.service.IBaseTreeService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;
import java.util.List;

public abstract class BaseTreeController<S extends IBaseTreeService, DTO extends BaseTreeDTO> extends BaseController<S, DTO> {
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    protected S iBaseService;

    @Valid
    @ApiOperation(value = "根据 parentId 查找所有 children 如果传空就查所有一级")
    @ApiOperationSupport(order = 1)
    @GetMapping("/find-by-pid")
    public ResultDTO<List<DTO>> findByParentId(String parentId) throws BaseException {
        List<DTO> children = iBaseService.findByParentId(parentId);
        return new ResultDTO(children);
    }

    @Valid
    @ApiOperation(value = "根据 子id 查 parent")
    @ApiOperationSupport(order = 1)
    @GetMapping("/find-parent-by-id")
    public ResultDTO<DTO> findParentById(String id) throws BaseException {
        DTO child = (DTO) iBaseService.findById(id);
        DTO parent = (DTO) iBaseService.findById(child.getParentId());
        return new ResultDTO(parent);
    }
}
