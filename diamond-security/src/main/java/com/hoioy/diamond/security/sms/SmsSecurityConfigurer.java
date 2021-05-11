package com.hoioy.diamond.security.sms;

import com.hoioy.diamond.common.util.CommonCacheUtil;
import com.hoioy.diamond.security.BaseDefaultSecurityResponseHandler;
import com.hoioy.diamond.security.ResponseHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/3/30
 * @describe
 */
public class SmsSecurityConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<SmsSecurityConfigurer<H>, H> {

    //是否启用逻辑
    private String enable = "on";

    //有效期，单位：秒
    private Integer maxWaitSecond = 300;

    //同一个手机号地址，每分钟限制发送短信次数
    private Integer limitPerMinutes = 1;

    private SmsUserDetailsService smsUserDetailsService;

    private SmsCodeHandler smsCodeHandler;

    private AuthenticationManager authenticationManager;

    private SmsAuthenticationFilter smsAuthenticationFilter;

    private final ApplicationContext context;

    private ResponseHandler responseHandle;

    public SmsSecurityConfigurer(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void init(H builder) throws Exception {
        super.init(builder);

        getSmsUserDetailsService();
        getAuthenticationManager();
        getSmsCodeHandler();
        getResponseHandle();
    }

    @Override
    public void configure(H http) {
        http.authenticationProvider(new SmsAuthenticationProvider(smsUserDetailsService, smsCodeHandler));

        smsAuthenticationFilter = getSmsAuthenticationFilter();
        smsAuthenticationFilter.setAuthenticationManager(authenticationManager);
        smsAuthenticationFilter.setResponseHandle(this.responseHandle);
        smsAuthenticationFilter.setSmsCodeHandler(this.smsCodeHandler);
        //TODO zhaozhao为了将此类移动到diamond security模块中，暂时注释，改用外部配置的方式
//        smsAuthenticationFilter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
        http.addFilterBefore(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public SmsSecurityConfigurer<H> authenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        getSmsAuthenticationFilter();
        this.smsAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        return this;
    }

    /**
     * 设置成功或失败的策略接口
     */
    public SmsSecurityConfigurer<H> responseHandle(ResponseHandler handle) {
        Assert.notNull(handle, "ResponseHandle cannot be null");
        this.responseHandle = handle;
        return this;
    }

    /**
     * 验证码有效期,单位秒
     *
     * @param maxWaitSecond
     * @return
     */
    public SmsSecurityConfigurer<H> maxWaitSecond(Integer maxWaitSecond) {
        this.maxWaitSecond = maxWaitSecond;
        return this;
    }

    /**
     * 自定义验证码逻辑实现,配置之后请勿再使用其他方法配置,全部自定义实现即可
     *
     * @param smsCodeHandler
     * @return
     */
    public SmsSecurityConfigurer<H> smsCodeHandle(SmsCodeHandler smsCodeHandler) {
        this.smsCodeHandler = smsCodeHandler;
        return this;
    }

    /**
     * 每分钟统一手机号发送短信次数
     *
     * @param limitPerMinutes
     * @return
     */
    public SmsSecurityConfigurer<H> limitPerMinutes(Integer limitPerMinutes) {
        this.limitPerMinutes = limitPerMinutes;
        return this;
    }

    /**
     * 是否开启短信验证码
     *
     * @param enable
     * @return
     */
    public SmsSecurityConfigurer<H> enable(String enable) {
        this.enable = enable;
        return this;
    }

    private SmsCodeHandler getSmsCodeHandler() {
        if (this.smsCodeHandler != null) {
            return this.smsCodeHandler;
        }
        CommonCacheUtil commonCacheUtil = context.getBean(CommonCacheUtil.class);
        SmsThirdSendService thirdSendService = context.getBean(SmsThirdSendService.class);
        this.smsCodeHandler = new DefaultSmsCodeHandle(enable, maxWaitSecond, limitPerMinutes, commonCacheUtil, thirdSendService);
        return this.smsCodeHandler;
    }

    private AuthenticationManager getAuthenticationManager() {
        if (this.authenticationManager != null) {
            return this.authenticationManager;
        }
        AuthenticationManager authenticationManager = context.getBean(AuthenticationManager.class);
        this.authenticationManager = authenticationManager;
        return this.authenticationManager;
    }

    private ResponseHandler getResponseHandle() {
        if (this.responseHandle != null) {
            return this.responseHandle;
        }
        this.responseHandle = new BaseDefaultSecurityResponseHandler();
        return this.responseHandle;
    }

    private SmsUserDetailsService getSmsUserDetailsService() {
        if (this.smsUserDetailsService != null) {
            return this.smsUserDetailsService;
        }
        SmsUserDetailsService smsUserDetailsService = context.getBean(SmsUserDetailsService.class);
        this.smsUserDetailsService = smsUserDetailsService;
        return this.smsUserDetailsService;
    }

    private SmsAuthenticationFilter getSmsAuthenticationFilter() {
        if (this.smsAuthenticationFilter != null) {
            return this.smsAuthenticationFilter;
        }
        smsAuthenticationFilter = new SmsAuthenticationFilter();
        return this.smsAuthenticationFilter;
    }
}
