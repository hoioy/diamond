package com.hoioy.jiayin.api;
import org.springframework.beans.factory.annotation.Autowired;
import com.hoioy.jiayin.service.IMessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 消息表 前端控制器
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Api( tags = "消息表接口" )
@RestController
@RequestMapping("/mapper/jiayin/message")
    public class MessageController extends BaseController<IMessageService,MessageDTO> {
        @Autowired
        private IMessageService messageService;


        @ApiOperation(value = "分页", notes = "分页")
        @PostMapping("/page")
        public ResultDTO page(@RequestBody PageDTO pageDTO)throws BaseException{
             PageDTO page=messageService.getPage(pageDTO);
             return new ResultDTO(page);
        }

    @Override
    public @Valid ResultDTO<String> save(MessageDTO dto) throws BaseException {
        String id =  messageService.publishMsg(dto);
        return super.save(dto);
    }
}

