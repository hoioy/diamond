package com.hoioy.jiayin.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MessagePageDTO;
import com.hoioy.jiayin.service.IMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "信息表接口")
@RestController
@RequestMapping("/jiayin/message")
public class MessageController extends BaseController<IMessageService, MessageDTO> {

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping("/page")
    public ResultDTO<PageDTO<MessagePageDTO>> page(@RequestBody PageDTO<MessagePageDTO> pageDTO) throws BaseException {
        PageDTO<MessagePageDTO> page = iBaseService.getPage(pageDTO);
        return new ResultDTO(page);
    }
    @ApiOperation(value = "取消发布", notes = "取消发布")
    @GetMapping("/cancelpublish/{msgId}")
    public ResultDTO<Boolean> cancelPublish(@PathVariable("msgId") String msgId) throws BaseException {
        Boolean  result =  iBaseService.cancelPublish(msgId);
        return new ResultDTO(result);
    }
}

