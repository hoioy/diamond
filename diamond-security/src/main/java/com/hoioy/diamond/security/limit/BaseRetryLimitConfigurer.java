package com.hoioy.diamond.security.limit;

import com.hoioy.diamond.common.util.CommonCacheUtil;
import com.hoioy.diamond.security.BaseDefaultSecurityResponseHandler;
import com.hoioy.diamond.security.BaseOrRequestMatcher;
import com.hoioy.diamond.security.ResponseHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

/**
 * 配置认证接口限制相关逻辑
 */
public class BaseRetryLimitConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<BaseRetryLimitConfigurer<H>, H> {
    //允许登录错误次数，对登录错误一定次数的用户进行封锁账号以及 IP 等措施
    private Integer retryTime = 5;

    //被锁定，不允许登录后恢复时间间隔
    private Long lockedRecoverSecond = 43200L;

    private final ApplicationContext context;

    private BaseOrRequestMatcher limitRequestMatcher;

    private ResponseHandler responseHandle;

    /**
     * 这种扩展写法参考了CsrfConfigurer，通过构造函数注入上下文
     *
     * @see HttpSecurity#csrf()
     */
    public BaseRetryLimitConfigurer(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void init(H builder) throws Exception {
        super.init(builder);
        getLimitRequestMatcher();
        getResponseHandle();
        //设置内置的匹配路径，包括formLogin默认的/login等登录端口
//        addRetryLimitRequestMatcher(new AntPathRequestMatcher("/smsLogin"));
        addRetryLimitRequestMatcher(new AntPathRequestMatcher("/login"));
    }

    @Override
    public void configure(H http) throws Exception {
        BaseProtectionLimitFilter filter = new BaseProtectionLimitFilter();
        filter.setLockedRecoverSecond(lockedRecoverSecond);
        filter.setRetryTime(retryTime);
        filter.setResponseHandle(responseHandle);

        CommonCacheUtil commonCacheUtil = context.getBean("commonCacheUtil", CommonCacheUtil.class);
        filter.setCommonCacheUtil(commonCacheUtil);

        filter.setLimitRequestMatcher(this.limitRequestMatcher);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    public BaseRetryLimitConfigurer<H> retryTime(Integer retryTime) {
        this.retryTime = retryTime;
        return this;
    }

    public BaseRetryLimitConfigurer<H> lockedRecoverSecond(Long lockedRecoverSecond) {
        this.lockedRecoverSecond = lockedRecoverSecond;
        return this;
    }

    /**
     * 设置成功或失败的策略接口
     *
     * @param handle
     */
    public BaseRetryLimitConfigurer<H> responseHandle(ResponseHandler handle) {
        Assert.notNull(handle, "ResponseHandle cannot be null");
        this.responseHandle = handle;
        return this;
    }

    /**
     * 用户自定义RequestMatcher扩展点。
     * 哪些接口走最大调用次数限制
     */
    public BaseRetryLimitConfigurer<H> addRetryLimitRequestMatcher(RequestMatcher requestMatcher) {
        if (requestMatcher != null) {
            getLimitRequestMatcher();
            this.limitRequestMatcher.addRequestMatcher(requestMatcher);
        }
        return this;
    }


    private RequestMatcher getLimitRequestMatcher() {
        if (this.limitRequestMatcher != null) {
            return this.limitRequestMatcher;
        }
        this.limitRequestMatcher = new BaseOrRequestMatcher();
        return this.limitRequestMatcher;
    }

    private ResponseHandler getResponseHandle() {
        if (this.responseHandle != null) {
            return this.responseHandle;
        }
        this.responseHandle = new BaseDefaultSecurityResponseHandler();
        return this.responseHandle;
    }

}
