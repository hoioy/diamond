package com.hoioy.jiayin.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.CommonDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MsgDraftDTO;
import com.hoioy.jiayin.service.IMessageService;
import com.hoioy.jiayin.service.IMsgDraftService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "消息草稿接口")
@RestController
@RequestMapping("/jiayin/msgDraft")
public class MsgDraftController extends BaseController<IMsgDraftService, MsgDraftDTO> {

   @Autowired
    private IMessageService messageService;

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping("/page")
    public ResultDTO page(@RequestBody PageDTO<MsgDraftDTO> pageDTO) throws BaseException {
        PageDTO page = iBaseService.getPage(pageDTO);
        return new ResultDTO(page);
    }

    @Valid
    @ApiOperation(value = "发布消息")
    @PostMapping("/publish")
    public ResultDTO publish(@RequestBody MsgDraftDTO dto) throws BaseException {
        MessageDTO messageDTO = new MessageDTO();
        BeanUtils.copyProperties(dto, messageDTO);
        MessageDTO save = (MessageDTO) messageService.save(messageDTO);
        if(save!=null){
            iBaseService.removeById(dto.getId());
        }
        return new ResultDTO(save);
    }

}

