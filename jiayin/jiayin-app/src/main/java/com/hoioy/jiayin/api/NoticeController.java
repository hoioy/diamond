package com.hoioy.jiayin.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.jiayin.dto.NoticeDTO;
import com.hoioy.jiayin.service.INoticeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "通知接口")
@RestController
@RequestMapping("/jiayin/notice")
public class NoticeController extends BaseController<INoticeService, NoticeDTO> {

}

