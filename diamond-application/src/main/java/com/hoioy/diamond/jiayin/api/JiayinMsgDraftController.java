package com.hoioy.diamond.jiayin.api;
import org.springframework.beans.factory.annotation.Autowired;
import com.hoioy.diamond.jiayin.service.IJiayinMsgDraftService;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hoioy.diamond.jiayin.domain.JiayinMsgDraft;
import com.hoioy.diamond.jiayin.dto.JiayinMsgDraftDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.hoioy.diamond.common.api.BaseController;

/**
 * <p>
 * 消息草稿 前端控制器
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-16
 */
@Api( tags = "消息草稿接口" )
@RestController
@RequestMapping("/jiayin/jiayinMsgDraft")
    public class JiayinMsgDraftController extends BaseController<IJiayinMsgDraftService,JiayinMsgDraftDTO> {
        @Autowired
        private IJiayinMsgDraftService jiayinMsgDraftService;


        @ApiOperation(value = "分页", notes = "分页")
        @PostMapping("/page")
        public ResultDTO page(@RequestBody PageDTO pageDTO)throws BaseException{
             PageDTO page=jiayinMsgDraftService.getPage(pageDTO);
             return new ResultDTO(page);
        }



    }
