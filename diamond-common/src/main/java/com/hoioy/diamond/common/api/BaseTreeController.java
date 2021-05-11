package com.hoioy.diamond.common.api;

import com.hoioy.diamond.common.dto.BaseTreeDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.service.IBaseTreeService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public abstract class BaseTreeController<S extends IBaseTreeService, DTO extends BaseTreeDTO> extends BaseController<S, DTO> {

    @ApiOperation(value = "1.根据 parentId 查找所有 children 如果传空就查所有一级")
    @ApiOperationSupport(order = 1)
    @GetMapping("/find-by-pid")
    public ResultDTO<List<DTO>> findByParentId(@Valid String parentId) throws BaseException {
        List<DTO> children = iBaseService.findByParentId(parentId);
        return new ResultDTO(children);
    }

    @ApiOperation(value = "2.根据 子id 查 parent")
    @ApiOperationSupport(order = 2)
    @GetMapping("/find-parent-by-id")
    public ResultDTO<DTO> findParentById(@Valid String id) throws BaseException {
        DTO child = (DTO) iBaseService.findById(id);
        DTO parent = (DTO) iBaseService.findById(child.getParentId());
        return new ResultDTO(parent);
    }

    @GetMapping("/move")
    @ApiOperation(value = "10.节点移动接口")
    @ApiOperationSupport(order = 10)
    public ResultDTO<Boolean> move(@RequestParam String currentParentId, @RequestParam String targetParentId, @RequestParam String currentId) {
        Boolean move = iBaseService.move(currentParentId, targetParentId, currentId);
        return new ResultDTO(move);
    }

    @GetMapping("/find-children-by-pid")
    @ApiOperation(value = "11.根据parentId查询整个树")
    @ApiOperationSupport(order = 11)
    public ResultDTO<List<DTO>> findChildrenByPath(@RequestParam String parentId) {
        List childrenByPath = iBaseService.findChildrenByParentId(parentId);
        return new ResultDTO(childrenByPath);
    }
}
