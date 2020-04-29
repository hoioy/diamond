package com.hoioy.jiayin.api;
import org.springframework.beans.factory.annotation.Autowired;
import com.hoioy.jiayin.service.IMsgTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hoioy.jiayin.dto.MsgTypeDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 消息类型 前端控制器
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Api( tags = "消息类型接口" )
@RestController
@RequestMapping("/jiayin/msgType")
    public class MsgTypeController extends BaseController<IMsgTypeService,MsgTypeDTO> {
        @Autowired
        private IMsgTypeService msgTypeService;


        @ApiOperation(value = "分页", notes = "分页")
        @PostMapping("/page")
        public ResultDTO page(@RequestBody PageDTO pageDTO)throws BaseException{
             PageDTO page=msgTypeService.getPage(pageDTO);
             return new ResultDTO(page);
        }



    }

