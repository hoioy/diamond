package com.hoioy.jiayin.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonSecurityUtils;
import com.hoioy.jiayin.dto.MsgPublishedDTO;
import com.hoioy.jiayin.service.IMsgPublishedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "我的发布")
@RestController
@RequestMapping("/jiayin/msgPublished")
public class MsgPublishedController extends BaseController<IMsgPublishedService, MsgPublishedDTO> {

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping("/page")
    public ResultDTO page(@RequestBody PageDTO<MsgPublishedDTO> pageDTO) throws BaseException {
        pageDTO.getFilters().setOpenid(CommonSecurityUtils.getCurrentLogin());

        PageDTO page = iBaseService.getPage(pageDTO);
        return new ResultDTO(page);
    }
}

