package com.hoioy.diamond.sys.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.sys.dto.ParameterInfoDTO;
import com.hoioy.diamond.sys.service.IParameterInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"08.参数操作接口"})
@RequestMapping("system/parameter")
public class ParameterController extends BaseController<IParameterInfoService, ParameterInfoDTO> {
    @ApiOperation(value = "添加参数")
    @RequestMapping(value = "/addParameter", method = RequestMethod.POST)
    public ResultDTO addParameter(@RequestBody @ApiParam(name = "ParameterInfoDTO", value = "参数dto") ParameterInfoDTO parameterInfoDto) {
        iBaseService.save(parameterInfoDto);
        return new ResultDTO();
    }

    @ApiOperation(value = "修改参数")
    @RequestMapping(value = "/updateParameter", method = RequestMethod.POST)
    public ResultDTO updateParameter(@RequestBody @ApiParam(name = "ParameterInfoDTO", value = "参数dto") ParameterInfoDTO parameterInfoDto) {
        iBaseService.update(parameterInfoDto);
        return new ResultDTO();
    }

    @ApiOperation(value = "删除参数")
    @RequestMapping(value = "/deleteParameter", method = RequestMethod.POST)
    public ResultDTO deleteParameter(@RequestBody @ApiParam(name = "ParameterInfoDTO", value = "参数dto") ParameterInfoDTO parameterInfoDto) {
        List ids = iBaseService.findIdsByParameterKeys(parameterInfoDto.getParameterKeys());
        iBaseService.removeByIds(ids);
        return new ResultDTO();
    }

    @ApiOperation(value = "查询参数")
    @RequestMapping(value = "/queryParameterList", method = RequestMethod.POST)
    public ResultDTO queryParameterList(@RequestBody @ApiParam(name = "ParameterInfoDTO", value = "参数dto") PageDTO parameterDto) {
        PageDTO pageDTO = iBaseService.getPage(parameterDto);
        return new ResultDTO(pageDTO);
    }
}
