package com.hoioy.diamond.security.sms;

import com.hoioy.diamond.common.dto.ResultDTO;

import java.util.Map;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/3/16
 * @describe 该类用于短信验证码的校验和发送短信, 你可以实现改接口编写自己的实现
 */
public interface SmsCodeHandler {
    /**
     * 检查手机验证码是否正确
     *
     * @param data 里面放发送验证码需要的数据
     * @return
     */
    Boolean checkSmsCode(Map<String, Object> data) throws SmsSecurityException;

    /**
     * 发送验证码
     *
     * @param phoneNum 手机号
     * @return 返回随机数，验证码登陆时使用
     */
    ResultDTO sendSmsCode(String phoneNum) throws SmsSecurityException;
}
