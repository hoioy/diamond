package com.hoioy.diamond.security.captcha;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.util.CommonBeanUtil;
import com.hoioy.diamond.common.util.CommonRedisUtil;
import com.hoioy.diamond.security.AuthenticationRequest;
import com.hoioy.diamond.security.exception.BaseSecurityException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CaptchaService {
    private static final String captchaEnableOn = "on";
    private static final String captchaRedisKeyPre = "diamond:captcha";
    private static final String captchaSameIpLimitRedisKeyPre = "diamond:ipLimitPerMinutes";

    //是否启用验证码逻辑
    @Value("${diamond.security.captcha-enable}")
    private String captchaEnable = captchaEnableOn;
    //验证码有效期，单位：秒
    @Value("${diamond.security.captcha-max-wait-second}")
    private long captchaMaxWaitSecond = 600l;
    //同一个IP地址，每分钟限制请求多少次验证码
    @Value("${diamond.security.captcha-same-ip-limit-per-minutes}")
    private long captchaSameIpLimitPerMinutes = 60;
    @Autowired
    private CommonRedisUtil commonRedisUtil;

    public ResultDTO captcha(String clientIp) {
        String ipLimitRedisKey = captchaSameIpLimitRedisKeyPre + ":" + clientIp;
        String time = commonRedisUtil.get(ipLimitRedisKey);
        if (StringUtils.isEmpty(time)) {
            //没有记录，说明之前没有错误认证
            time = captchaSameIpLimitPerMinutes + "";
            commonRedisUtil.set(ipLimitRedisKey, time, 60);
        }
        Integer t = Integer.parseInt(time);
        if (t <= 0) {
            throw new BaseSecurityException("每分钟只能获取60次验证码");
        }

        String captchaCode = (int) ((Math.random() * 9 + 1) * 1000) + "";
        String key = CommonBeanUtil.generateBeanId();
        commonRedisUtil.set(captchaRedisKeyPre + ":" + key, captchaCode, captchaMaxWaitSecond);

        Map<String, String> result = new HashMap<String, String>();
        result.put("captchaKey", key);
        result.put("captchaCode", captchaCode);
        commonRedisUtil.increment(ipLimitRedisKey, -1);
        return new ResultDTO(result);
    }

    public Boolean checkCaptcha(AuthenticationRequest authenticationRequest) {
        if (captchaEnableOn.equals(captchaEnable)) {
            String captchaKey = authenticationRequest.getCaptchaKey();
            String captchaCode = authenticationRequest.getCaptchaCode();
            //验证码校验逻辑启用
            if (StringUtils.isEmpty(captchaKey)) {
                throw new BaseSecurityException("验证码Key不可以为空");
            }
            String storedCaptchaCode = commonRedisUtil.get(captchaRedisKeyPre + ":" + captchaKey);
            if (StringUtils.isBlank(storedCaptchaCode) || !storedCaptchaCode.equals(captchaCode)) {
                throw new BaseSecurityException("验证码错误或已过期，请刷新验证码");
            }
        }
        return true;
    }
}
