package com.hoioy.jiayin.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.jiayin.dto.MsgTypeDTO;
import com.hoioy.jiayin.service.IMsgTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 消息类型 前端控制器
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Api(tags = "消息类型接口")
@RestController
@RequestMapping("/jiayin/msgType")
public class MsgTypeController extends BaseController<IMsgTypeService, MsgTypeDTO> {
    @Autowired
    private IMsgTypeService msgTypeService;

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping("/page")
    public ResultDTO page(@RequestBody PageDTO<MsgTypeDTO> pageDTO) throws BaseException {
        PageDTO page = msgTypeService.getPage(pageDTO);
        return new ResultDTO(page);
    }

    @ApiOperation(value = "根据parenId查询", notes = "根据parenId查询,parentId为null或者空字符串，则查询根节点")
    @GetMapping("/findByParentId")
    public ResultDTO findByParentId(@RequestParam("parentId") String parentId) throws BaseException {
        List<MsgTypeDTO> msgTypeDTOs = msgTypeService.findByParentId(parentId);
        return new ResultDTO(msgTypeDTOs);
    }

    @ApiOperation(value = "根据childId找parent", notes = "根据childId找parent")
    @GetMapping("/findParentByChildId/{childId}")
    public ResultDTO findParentByChildId(@PathVariable String childId) throws BaseException {
        MsgTypeDTO msgTypeDTO = msgTypeService.findParentByChildId(childId);
        return new ResultDTO(msgTypeDTO);
    }
}

