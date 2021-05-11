package com.hoioy.diamond.sys.api;

import com.hoioy.diamond.common.api.BaseTreeController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.sys.dto.DeptInfoDTO;
import com.hoioy.diamond.sys.service.IDeptInfoService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"06.部门操作接口"})
@ApiSupport(order = 6)
@RequestMapping("/sys/dept")
public class DeptInfoController extends BaseTreeController<IDeptInfoService, DeptInfoDTO> {

    //TODO zhaozhao 不建议全量查询
    @ApiOperation(value = "8.根据条件查询全部部门tree信息，不分页，返回tree结构数组")
    @ApiOperationSupport(order = 8)
    @GetMapping(value = "/query/all/tree")
    public ResultDTO<List> queryAllTree() {
        List<DeptInfoDTO> list = iBaseService.findAll();
        List<DeptInfoDTO> menuTree = iBaseService.listToTree(list, null);
        return new ResultDTO<>(menuTree);
    }

    //TODO zhaozhao 不建议全量查询
    @ApiOperation(value = "9.根据条件查询全部部门信息，不分页，非tree结构")
    @ApiOperationSupport(order = 9)
    @GetMapping(value = "/query/all")
    public ResultDTO<PageDTO> queryAll() {
        List<DeptInfoDTO> list = iBaseService.findAll();
        PageDTO pageDTO = new PageDTO();
        pageDTO.setList(list);
        return new ResultDTO<>(pageDTO);
    }
}
