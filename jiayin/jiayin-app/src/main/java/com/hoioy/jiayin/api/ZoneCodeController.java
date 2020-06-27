package com.hoioy.jiayin.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.jiayin.dto.MsgTypeDTO;
import com.hoioy.jiayin.dto.ZoneCodeDTO;
import com.hoioy.jiayin.service.IZoneCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "区划表接口")
@RestController
@RequestMapping("/jiayin/zonecode")
public class ZoneCodeController extends BaseController<IZoneCodeService, ZoneCodeDTO> {

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping("/page")
    public ResultDTO page(@RequestBody PageDTO<ZoneCodeDTO> pageDTO) throws BaseException {
        PageDTO page = iBaseService.getPage(pageDTO);
        return new ResultDTO(page);
    }

    @ApiOperation(value = "根据parenId查询", notes = "根据parenId查询,parentId为null或者空字符串，则查询根节点")
    @GetMapping("/findByParentId")
    public ResultDTO findByParentId(@RequestParam(name = "parentId",required = false) String parentId) throws BaseException {
        List<ZoneCodeDTO> dtos = iBaseService.findByParentId(parentId);
         return new ResultDTO(dtos);
    }

}

