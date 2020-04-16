package com.hoioy.diamond.jiayin.api;
import org.springframework.beans.factory.annotation.Autowired;
import com.hoioy.diamond.jiayin.service.IJiayinMsgHistoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hoioy.diamond.jiayin.domain.JiayinMsgHistory;
import com.hoioy.diamond.jiayin.dto.JiayinMsgHistoryDTO;
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
 * 观看历史 前端控制器
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-16
 */
@Api( tags = "观看历史接口" )
@RestController
@RequestMapping("/jiayin/jiayinMsgHistory")
    public class JiayinMsgHistoryController extends BaseController<IJiayinMsgHistoryService,JiayinMsgHistoryDTO> {
        @Autowired
        private IJiayinMsgHistoryService jiayinMsgHistoryService;


        @ApiOperation(value = "分页", notes = "分页")
        @PostMapping("/page")
        public ResultDTO page(@RequestBody PageDTO pageDTO)throws BaseException{
             PageDTO page=jiayinMsgHistoryService.getPage(pageDTO);
             return new ResultDTO(page);
        }



    }

