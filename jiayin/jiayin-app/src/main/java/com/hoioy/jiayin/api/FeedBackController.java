package com.hoioy.jiayin.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.jiayin.dto.FeedBackDTO;
import com.hoioy.jiayin.service.IFeedBackService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "问题反馈")
@RestController
@RequestMapping("/jiayin/feedback")
public class FeedBackController extends BaseController<IFeedBackService, FeedBackDTO> {

}

