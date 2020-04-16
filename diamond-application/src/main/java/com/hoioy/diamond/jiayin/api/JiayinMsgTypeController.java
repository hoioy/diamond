package com.hoioy.diamond.jiayin.api;
import org.springframework.beans.factory.annotation.Autowired;
import com.hoioy.diamond.jiayin.service.IJiayinMsgTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hoioy.diamond.jiayin.domain.JiayinMsgType;
import com.hoioy.diamond.jiayin.dto.JiayinMsgTypeDTO;
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
 * 消息类型 前端控制器
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-16
 */
@Api( tags = "消息类型接口" )
@RestController
@RequestMapping("/jiayin/jiayinMsgType")
    public class JiayinMsgTypeController extends BaseController<IJiayinMsgTypeService,JiayinMsgTypeDTO> {
        @Autowired
        private IJiayinMsgTypeService jiayinMsgTypeService;


        @ApiOperation(value = "分页", notes = "分页")
        @PostMapping("/page")
        public ResultDTO page(@RequestBody PageDTO pageDTO)throws BaseException{
             PageDTO page=jiayinMsgTypeService.getPage(pageDTO);
             return new ResultDTO(page);
        }



    }

