package com.hoioy.jiayin.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.jiayin.dto.MessageDTO;
import com.hoioy.jiayin.dto.MessagePageDTO;
import com.hoioy.jiayin.service.IPublishService;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publish")
public class PublishController {
    @Autowired
    private IPublishService iPublishService;

    @ApiOperation(value = "保存为草稿", notes = "保存为草稿")
    @PostMapping("/savedraft")
    public ResultDTO savedraft(@RequestBody MessageDTO messageDTO) throws BaseException {
        MessageDTO messageDTO1 = iPublishService.saveDraft(messageDTO);
        return new ResultDTO(messageDTO1);
    }

    @ApiOperation(value = "发布", notes = "发布")
    @PostMapping("/publish")
    public ResultDTO publish(@RequestBody MessageDTO messageDTO) throws BaseException {
        MessageDTO messageDTO1 = iPublishService.publish(messageDTO);
        return new ResultDTO(messageDTO1);
    }

}
