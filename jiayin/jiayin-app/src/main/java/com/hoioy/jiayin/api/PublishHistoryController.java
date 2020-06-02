package com.hoioy.jiayin.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.jiayin.dto.PublishHistoryDTO;
import com.hoioy.jiayin.service.IPublishHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈哲
 * @since 2020-05-21
 */
@Api( tags = "我的发布" )
@RestController
@RequestMapping("/jiayin/publishHistory")
    public class PublishHistoryController extends BaseController<IPublishHistoryService, PublishHistoryDTO> {
        @Autowired
        private IPublishHistoryService publishHistoryService;


        @ApiOperation(value = "分页", notes = "分页")
        @PostMapping("/page")
        public ResultDTO page(@RequestBody PageDTO<PublishHistoryDTO> pageDTO)throws BaseException {
             PageDTO page=publishHistoryService.getPage(pageDTO);
             return new ResultDTO(page);
        }



    }

