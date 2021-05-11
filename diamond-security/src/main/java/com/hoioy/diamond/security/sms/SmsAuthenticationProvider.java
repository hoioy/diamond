package com.hoioy.diamond.security.sms;

import com.hoioy.diamond.security.BaseAuthenticationToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/3/16
 * @describe 验证码登陆的provider，处理SmsAuthenticationFilter拦截的请求
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private static final Log logger = LogFactory.getLog(SmsAuthenticationProvider.class);

    private SmsUserDetailsService smsUserDetailsService;

    private SmsCodeHandler smsCodeHandler;


    public SmsAuthenticationProvider(SmsUserDetailsService smsUserDetailsService, SmsCodeHandler smsCodeService) {
        this.smsUserDetailsService = smsUserDetailsService;
        this.smsCodeHandler = smsCodeService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws SmsSecurityException {
        String phoneNum = (String) authentication.getPrincipal();

        Map<String, Object> credentials = (Map<String, Object>) authentication.getCredentials();

        smsCodeHandler.checkSmsCode(credentials);

        UserDetails userDetails = smsUserDetailsService.loadByPhoneNum(phoneNum);

        BaseAuthenticationToken authenticationToken = new BaseAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());

        return authenticationToken;


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (BaseAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
