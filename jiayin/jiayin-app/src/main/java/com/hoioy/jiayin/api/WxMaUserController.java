package com.hoioy.jiayin.api;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.lang.UUID;
import com.hoioy.jiayin.conf.WxMaConfiguration;
import com.hoioy.jiayin.seucrity.WeChatToken;
import com.hoioy.jiayin.seucrity.converter.MiniAppJwtAccessTokenConverter;
import com.hoioy.jiayin.utils.JsonUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RestController
@RequestMapping("/wx/user/{appid}")
public class WxMaUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MiniAppJwtAccessTokenConverter miniAppJwtAccessTokenConverter;

    /**
     * 登陆接口
     */
    @GetMapping("/login")
    public String login(@PathVariable String appid, String code) {
//        if (StringUtils.isBlank(code)) {
//            return "empty jscode";
//        }
//
//        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        try {
//            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            WxMaJscode2SessionResult session =  new WxMaJscode2SessionResult();
            session.setOpenid("123123");
            session.setSessionKey("3534343");
            session.setUnionid("asdasd");
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());
//            authenticate(session);
            final String token = miniAppJwtAccessTokenConverter.generateToken(session);
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return token;
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return e.toString();
        }
    }

    /**
     * Authenticates the user.
     */
    private void authenticate(WxMaJscode2SessionResult wxMaJscode2SessionResult) {
        Objects.requireNonNull(wxMaJscode2SessionResult.getOpenid());
        Objects.requireNonNull(wxMaJscode2SessionResult.getSessionKey());
        Objects.requireNonNull(wxMaJscode2SessionResult.getUnionid());
        UUID uuid = UUID.randomUUID();
        WeChatToken authRequest = new WeChatToken(wxMaJscode2SessionResult,uuid);
        Authentication auth = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/info")
    public String info(@PathVariable String appid, String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone(@PathVariable String appid, String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(phoneNoInfo);
    }

}
