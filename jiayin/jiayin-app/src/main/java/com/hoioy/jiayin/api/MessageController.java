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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

