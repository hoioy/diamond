package com.hoioy.jiayin.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.jiayin.dto.MsgCountDTO;
import com.hoioy.jiayin.service.IMsgCountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 消息次数 前端控制器
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Api(tags = "消息次数接口")
@RestController
@RequestMapping("/mapper/jiayin/msgCount")
public class MsgCountController extends BaseController<IMsgCountService, MsgCountDTO> {
    @Autowired
    private IMsgCountService msgCountService;


    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping("/page")
    public ResultDTO page(@RequestBody PageDTO pageDTO) throws BaseException {
        PageDTO page = msgCountService.getPage(pageDTO);
        return new ResultDTO(page);
    }


}
