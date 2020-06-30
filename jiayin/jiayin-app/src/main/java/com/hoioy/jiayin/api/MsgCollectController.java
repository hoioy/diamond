package com.hoioy.jiayin.api;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.jiayin.dto.MsgCollectDTO;
import com.hoioy.jiayin.service.IMsgCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(tags = "信息收藏接口")
@RestController
@RequestMapping("/jiayin/msgCollect")
public class MsgCollectController extends BaseController<IMsgCollectService, MsgCollectDTO> {
    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping("/page")
    public ResultDTO page(@RequestBody PageDTO<MsgCollectDTO> pageDTO) throws BaseException {
        PageDTO page = iBaseService.getPage(pageDTO);
        return new ResultDTO(page);
    }

//    @ApiOperation(value = "根据msgId查询是否收藏")
//    @ApiOperationSupport(order = 6)
//    @GetMapping("/{id}")
//    public ResultDTO selectIsCollect(@RequestBody MsgCollectDTO msgCollectDTO) throws BaseException {
//        Optional<DTO> byId = iBaseService.findById(id);
//        return new ResultDTO<DTO>(byId.get());
//    }


}

