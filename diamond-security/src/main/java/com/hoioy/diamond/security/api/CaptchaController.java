package com.hoioy.diamond.security.api;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.util.WebSiteUtil;
import com.hoioy.diamond.security.captcha.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"00.验证码接口"})
@RestController
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    /**
     * 前后端分离，无session。后端生成的验证码不能通过session直接拿到，
     * 所以后端生成的验证码会保存在redis中，前端登录时带上key和value，后端根据Key去redis取值，
     * 再和前端传进来的value对比，完成验证码验证
     *
     * @param httpServletRequest
     * @return
     */
    @ApiOperation(value = "获取验证码")
    @GetMapping(value = "/captcha")
    public ResultDTO captcha(HttpServletRequest httpServletRequest) {
        String ip = WebSiteUtil.getIpAddress(httpServletRequest);
        return captchaService.captcha(ip);
    }
}
