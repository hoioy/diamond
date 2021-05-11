package com.hoioy.diamond.security.captcha;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.util.CommonBeanUtil;
import com.hoioy.diamond.common.util.CommonCacheUtil;
import com.hoioy.diamond.security.BaseAuthenticationRequestDTO;
import com.hoioy.diamond.security.BaseSecurityException;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 默认生成验证码处理类
 */
public class BaseDefaultCaptchaHandler implements BaseCaptchaHandler {

    private static final String captchaEnableOn = "on";
    private static final String captchaRedisKeyPre = "base:captcha";
    private static final String captchaSameIpLimitRedisKeyPre = "base:ipLimitPerMinutes";

    //是否启用验证码逻辑
    private String captchaEnable ;

    //验证码有效期，单位：秒
    private Long captchaMaxWaitSecond ;

    //同一个IP地址，每分钟限制请求多少次验证码
    private Long captchaSameIpLimitPerMinutes ;

    private CommonCacheUtil commonCacheUtil;

    public BaseDefaultCaptchaHandler(String captchaEnable, Long captchaMaxWaitSecond,
                                     Long captchaSameIpLimitPerMinutes,CommonCacheUtil commonCacheUtil) {
        this.captchaEnable = captchaEnable;
        this.captchaMaxWaitSecond = captchaMaxWaitSecond;
        this.captchaSameIpLimitPerMinutes = captchaSameIpLimitPerMinutes;
        this.commonCacheUtil = commonCacheUtil;
    }

    @Override
    public ResultDTO createCaptcha(String clientIp) {
        String ipLimitRedisKey = captchaSameIpLimitRedisKeyPre + ":" + clientIp;
        String time = commonCacheUtil.get(ipLimitRedisKey);
        if (StrUtil.isBlank(time)) {
            //没有记录，说明之前没有错误认证
            time = captchaSameIpLimitPerMinutes + "";
            commonCacheUtil.set(ipLimitRedisKey, time, 60);
        }
        Integer t = Integer.parseInt(time);
        if (t <= 0) {
            throw new BaseSecurityException("每分钟只能获取" + captchaSameIpLimitPerMinutes + "次验证码");
        }

        String captchaCode = (int) ((Math.random() * 9 + 1) * 1000) + "";
        String key = CommonBeanUtil.generateBeanId();
        commonCacheUtil.set(captchaRedisKeyPre + ":" + key, captchaCode, captchaMaxWaitSecond);

        commonCacheUtil.increment(ipLimitRedisKey, -1);
        return new ResultDTO(MapUtil.builder()
                .put("captchaKey", key)
                .put("captchaCode", captchaCode)
                .build());
    }

    @Override
    public Boolean checkCaptcha(BaseAuthenticationRequestDTO authenticationRequest) {
        if (captchaEnableOn.equals(captchaEnable)) {
            String captchaKey = authenticationRequest.getCaptchaKey();
            String captchaCode = authenticationRequest.getCaptchaCode();
            //验证码校验逻辑启用
            if (StrUtil.isBlank(captchaKey)) {
                throw new BaseSecurityException("验证码Key不可以为空");
            }
            String storedCaptchaCode = commonCacheUtil.get(captchaRedisKeyPre + ":" + captchaKey);
            if (StrUtil.isBlank(storedCaptchaCode) || !storedCaptchaCode.equals(captchaCode)) {
                throw new BaseSecurityException("验证码错误或已过期，请刷新验证码");
            }
        }
        return true;
    }
}
