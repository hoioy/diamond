package com.hoioy.diamond.security.sms;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.util.CommonBeanUtil;
import com.hoioy.diamond.common.util.CommonCacheUtil;
import com.hoioy.diamond.security.BaseConstants;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/3/17
 * @describe
 *
 */
public class DefaultSmsCodeHandle implements SmsCodeHandler {
    private static final Log logger = LogFactory.getLog(DefaultSmsCodeHandle.class);
    private static final String PHONE_CODE_CACHE_KEY_PRE = "base:phoneCode";
    private static final String PHONE_CODE_SAME_LIMIT_CACHE_KEY_PRE = "base:phoneLimitPerMinutes";

    //是否启用逻辑
    private String enable="on";

    //有效期，单位：秒
    private Integer maxWaitSecond=300;

    //同一个手机号地址，每分钟限制发送短信次数
    private Integer limitPerMinutes;

    private CommonCacheUtil commonCacheUtil;

    private SmsThirdSendService thirdSendService;

    public DefaultSmsCodeHandle(String enable, Integer maxWaitSecond, Integer limitPerMinutes, CommonCacheUtil commonCacheUtil, SmsThirdSendService thirdSendService) {
        this.enable = enable;
        this.maxWaitSecond = maxWaitSecond;
        this.limitPerMinutes = limitPerMinutes;
        this.commonCacheUtil = commonCacheUtil;
        this.thirdSendService = thirdSendService;
    }

    @Override
    public Boolean checkSmsCode(Map<String, Object> data) throws SmsSecurityException{
        logger.error("开始检查短信验证码");
        if (enable.equals(BaseConstants.ENABLE)) {

            String phoneCode = (String) data.get(BaseConstants.PHONECODE);

            String phoneCodeKey = (String) data.get(BaseConstants.PHONECODE_KEY);
            //校验逻辑启用
            if (StrUtil.isBlank(phoneCode)) {
                throw new SmsSecurityException(5002,"手机校验码Key不可以为空");
            }
            String storedCaptchaCode = commonCacheUtil.get(PHONE_CODE_CACHE_KEY_PRE + ":" + phoneCodeKey);
            if (StrUtil.isBlank(storedCaptchaCode) || !storedCaptchaCode.equals(phoneCode)) {
                throw new SmsSecurityException(5003,"手机校验码错误或已过期，请重新获取手机校验码");
            }
        }
        return true;
    }


    @Override
    public ResultDTO sendSmsCode(String phoneNumber) throws SmsSecurityException {
        if (StrUtil.isBlank(phoneNumber)) {
            throw new SmsSecurityException(5004,"手机号不能为空");
        }

        //次数校验
        String limitKey = PHONE_CODE_CACHE_KEY_PRE + ":" + phoneNumber;
        String time = commonCacheUtil.get(limitKey);
        if (StrUtil.isBlank(time)) {
            //没有记录，说明一定时间内没有请求过
            time = limitPerMinutes + "";
            commonCacheUtil.set(limitKey, time, 60);
        }
        Integer t = Integer.parseInt(time);
        if (t <= 0 && enable.equals(BaseConstants.ENABLE)) {
            throw new SmsSecurityException(5005,"同一个手机号每分钟发送" + limitPerMinutes + "次短信");
        }

        //生成随机码
        String phoneCode = (int) ((Math.random() * 9 + 1) * 1000) + "";
        String phoneCodeKey = CommonBeanUtil.generateBeanId();
        commonCacheUtil.set(PHONE_CODE_CACHE_KEY_PRE + ":" + phoneCodeKey, phoneCode, maxWaitSecond);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("phoneCodeKey", phoneCodeKey);
        //关闭验证码
        if (enable.equals(BaseConstants.ENABLE)) {
            result.put("phoneCode", phoneCode);
        }

        //开始调用第三方服务发送短信
        Boolean send = thirdSendService.send(result);

        //次数减去1
        commonCacheUtil.increment(limitKey, -1);

        return new ResultDTO(result);
    }
}
