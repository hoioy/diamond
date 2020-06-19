package com.hoioy.jiayin.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.jiayin.dto.AdvertDTO;
import com.hoioy.jiayin.service.IAdvertService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "广告接口")
@RestController
@RequestMapping("/jiayin/advert")
public class AdvertController extends BaseController<IAdvertService, AdvertDTO> {

}

