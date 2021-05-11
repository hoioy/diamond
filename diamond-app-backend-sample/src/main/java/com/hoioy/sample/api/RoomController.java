package com.hoioy.sample.api;

import com.hoioy.diamond.common.api.CommonController;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.hoioy.sample.dto.RoomDTO;
import com.hoioy.sample.service.IRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = {"206. 教室管理。继承框架CommonXX的弱约束写法"})
@RequestMapping("/sample/room")
public class RoomController extends CommonController<IRoomService, RoomDTO> {
    @Valid
    @ApiOperation(value = "1.新增")
    @ApiOperationSupport(order = 1)
    @PostMapping
    public ResultDTO<RoomDTO> create(@RequestBody RoomDTO dto) throws BaseException {
        RoomDTO resultDTO = (RoomDTO) iBaseService.create(dto);
        return new ResultDTO(resultDTO);
    }

    @ApiOperation(value = "2.批量新增")
    @ApiOperationSupport(order = 2)
    @PostMapping("/batchCreate")
    public ResultDTO batchCreate(@RequestBody List<RoomDTO> dtos) throws BaseException {
        iBaseService.batchCreate(dtos);
        return new ResultDTO();
    }

    @Valid
    @ApiOperation(value = "3.修改")
    @ApiOperationSupport(order = 3)
    @PutMapping
    public ResultDTO<String> update(@RequestBody RoomDTO dto) throws BaseException {
        RoomDTO resultDTO = (RoomDTO) iBaseService.update(dto);
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
    public ResultDTO<RoomDTO> selectById(@PathVariable("id") String id) throws BaseException {
        RoomDTO byId = iBaseService.findById(id);
        return new ResultDTO(byId);
    }
}
