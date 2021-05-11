package com.hoioy.sample.api;

import com.hoioy.diamond.common.dto.CommonPageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.hoioy.sample.dto.TeacherDTO;
import com.hoioy.sample.service.ITeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = {"205. 老师管理。不继承框架基类，Mybatis原始写法"})
@RequestMapping("/sample/teacher")
public class TeacherController {
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    protected ITeacherService iBaseService;

    @Autowired
    protected MessageSource messageSource;

    @Valid
    @ApiOperation(value = "1.新增")
    @ApiOperationSupport(order = 1)
    @PostMapping
    public ResultDTO<TeacherDTO> create(@RequestBody TeacherDTO dto) throws BaseException {
        TeacherDTO resultDTO = (TeacherDTO) iBaseService.create(dto);
        return new ResultDTO(resultDTO);
    }

    @ApiOperation(value = "2.批量新增")
    @ApiOperationSupport(order = 2)
    @PostMapping("/batchCreate")
    public ResultDTO batchCreate(@RequestBody List<TeacherDTO> dtos) throws BaseException {
        iBaseService.batchCreate(dtos);
        return new ResultDTO();
    }

    @Valid
    @ApiOperation(value = "3.修改")
    @ApiOperationSupport(order = 3)
    @PutMapping
    public ResultDTO<String> update(@RequestBody TeacherDTO dto) throws BaseException {
        TeacherDTO resultDTO = (TeacherDTO) iBaseService.update(dto);
        return new ResultDTO(resultDTO);
    }

    @ApiOperation(value = "4.根据id删除")
    @ApiOperationSupport(order = 4)
    @DeleteMapping("/{id}")
    public ResultDTO<Boolean> deleteById(@PathVariable("id") String id) throws BaseException {
        Boolean isSuccess = iBaseService.removeById(id);
        return new ResultDTO<>(isSuccess);
    }

    @ApiOperation(value = "5.根据多个id批量删除")
    @ApiOperationSupport(order = 5)
    @DeleteMapping("/deleteByIds")
    public ResultDTO<Boolean> deleteByIds(@RequestBody List<String> ids) throws BaseException {
        Boolean isSuccess = iBaseService.removeByIds(ids);
        return new ResultDTO<>(isSuccess);
    }

    @ApiOperation(value = "6.根据id查询")
    @ApiOperationSupport(order = 6)
    @GetMapping("/{id}")
    public ResultDTO<TeacherDTO> selectById(@PathVariable("id") String id) throws BaseException {
        TeacherDTO byId = iBaseService.findById(id);
        return new ResultDTO(byId);
    }

    @Valid
    @ApiOperation(value = "7.普通单表分页查询")
    @ApiOperationSupport(order = 7)
    @PostMapping(value = "/search")
    public ResultDTO<CommonPageDTO<TeacherDTO>> selectByPage(@RequestBody CommonPageDTO<TeacherDTO> pageDTO) {
        pageDTO = iBaseService.getPage(pageDTO);
        return new ResultDTO(pageDTO);
    }
}
