package com.hoioy.diamond.security.captcha;

import com.hoioy.diamond.common.util.CommonCacheUtil;
import com.hoioy.diamond.security.BaseDefaultSecurityResponseHandler;
import com.hoioy.diamond.security.BaseOrRequestMatcher;
import com.hoioy.diamond.security.ResponseHandler;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 配置Captcha相关逻辑
 */
public class BaseCaptchaConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<BaseCaptchaConfigurer<H>, H> {
    private String captchUrl = "/captcha";

    //是否启用验证码逻辑
    private String captchaEnable = "on";

    //验证码有效期，单位：秒
    private Long captchaMaxWaitSecond = 600L;

    //同一个IP地址，每分钟限制请求多少次验证码
    private Long captchaSameIpLimitPerMinutes = 60L;

    private RequestMatcher createCaptchaRequestMatcher;

    private BaseOrRequestMatcher checkOrCaptchaRequestMatcher;

    private BaseCaptchaHandler baseCaptchaHandler;

    private ResponseHandler responseHandle;

    private final ApplicationContext context;

    /**
     * 这种扩展写法参考了CsrfConfigurer，通过构造函数注入上下文
     *
     * @see HttpSecurity#csrf()
     */
    public BaseCaptchaConfigurer(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void init(H builder) throws Exception {
        super.init(builder);
        getCheckCaptchaRequestMatcher();
        getCreateCaptchaRequestMatcher();
        getBaseCaptchaHandler();
        getResponseHandle();
        //设置内置的匹配路径，包括formLogin默认的/login等登录端口
//        addCheckPointRequestMatcher(new AntPathRequestMatcher("/smsSendCode"));
//        addCheckPointRequestMatcher(new AntPathRequestMatcher("/smsLogin"));
        addCheckPointRequestMatcher(new AntPathRequestMatcher("/login"));
    }

    @Override
    public void configure(H http) throws Exception {
        BaseCaptchaFilter captchaFilter = new BaseCaptchaFilter();
        captchaFilter.setBaseCaptchaHandler(this.baseCaptchaHandler);
        captchaFilter.setCreateCaptchaRequestMatcher(this.createCaptchaRequestMatcher);
        captchaFilter.setHandle(this.responseHandle);
        //设置默认的校验验证码Filter
        captchaFilter.setCheckOrCaptchaRequestMatcher(this.checkOrCaptchaRequestMatcher);

        captchaFilter = postProcess(captchaFilter);

        http.addFilterBefore(captchaFilter, LogoutFilter.class);
    }
    /**
     * 开启和关闭数字验证码
     * @param captchaEnable
     * @return
     */
    public BaseCaptchaConfigurer<H> captchaEnable(String captchaEnable) {
        this.captchaEnable = captchaEnable;
        return this;
    }

    /**
     * 自定义验证码逻辑实现,配置之后请勿再使用其他方法配置,全部自定义实现即可
     * @param baseCaptchaHandler
     * @return
     */
    public BaseCaptchaConfigurer<H> captchaHandler(BaseCaptchaHandler baseCaptchaHandler) {
        this.baseCaptchaHandler = baseCaptchaHandler;
        return this;
    }

    /**
     * 验证码有效期
     * @param captchaMaxWaitSecond
     * @return
     */
    public BaseCaptchaConfigurer<H> captchaMaxWaitSecond(Long captchaMaxWaitSecond) {
        this.captchaMaxWaitSecond = captchaMaxWaitSecond;
        return this;
    }

    /**
     * 设置成功或失败的策略接口
     */
    public BaseCaptchaConfigurer<H> responseHandle(ResponseHandler handle) {
        Assert.notNull(handle, "ResponseHandle cannot be null");
        this.responseHandle = handle;
        return this;
    }

    /**
     * 每分钟获取验证码的次数
     * @param captchaSameIpLimitPerMinutes
     * @return
     */
    public BaseCaptchaConfigurer<H> captchaSameIpLimitPerMinutes(Long captchaSameIpLimitPerMinutes) {
        this.captchaSameIpLimitPerMinutes = captchaSameIpLimitPerMinutes;
        return this;
    }

    /**
     * 用户自定义RequestMatcher扩展点。
     * 哪些接口需要走验证码校验，是具体项目自己来定义
     */
    public BaseCaptchaConfigurer<H> addCheckPointRequestMatcher(RequestMatcher requestMatcher) {
        if (requestMatcher != null) {
            getCheckCaptchaRequestMatcher();
            this.checkOrCaptchaRequestMatcher.addRequestMatcher(requestMatcher);
        }
        return this;
    }

    public BaseCaptchaConfigurer<H> addAllCheckPointRequestMatcher(List<RequestMatcher> requestMatcherList) {
        if (CollectionUtil.isNotEmpty(requestMatcherList)) {
            getCheckCaptchaRequestMatcher();
            this.checkOrCaptchaRequestMatcher.addAllRequestMatcher(requestMatcherList);
        }
        return this;
    }

    //配置生成token的Matcher
    private RequestMatcher getCreateCaptchaRequestMatcher() {
        if (this.createCaptchaRequestMatcher != null) {
            return this.createCaptchaRequestMatcher;
        }
        this.createCaptchaRequestMatcher = new AntPathRequestMatcher(this.captchUrl, "GET");
        return this.createCaptchaRequestMatcher;
    }

    //配置校验token的Matcher
    private RequestMatcher getCheckCaptchaRequestMatcher() {
        if (this.checkOrCaptchaRequestMatcher != null) {
            return this.checkOrCaptchaRequestMatcher;
        }
        this.checkOrCaptchaRequestMatcher = new BaseOrRequestMatcher();
        return this.checkOrCaptchaRequestMatcher;
    }

    //配置处理器
    private BaseCaptchaHandler getBaseCaptchaHandler() {
        if (this.baseCaptchaHandler != null) {
            return this.baseCaptchaHandler;
        }
        CommonCacheUtil commonCacheUtil = context.getBean("commonCacheUtil", CommonCacheUtil.class);
        this.baseCaptchaHandler = new BaseDefaultCaptchaHandler(captchaEnable, captchaMaxWaitSecond, captchaSameIpLimitPerMinutes, commonCacheUtil);
        return this.baseCaptchaHandler;
    }

    private ResponseHandler getResponseHandle() {
        if (this.responseHandle != null) {
            return this.responseHandle;
        }
        this.responseHandle = new BaseDefaultSecurityResponseHandler();
        return this.responseHandle;
    }
}
