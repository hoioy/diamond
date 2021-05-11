package com.hoioy.diamond.security.qrcode;

import com.hoioy.diamond.common.util.CommonCacheUtil;
import com.hoioy.diamond.security.BaseDefaultSecurityResponseHandler;
import com.hoioy.diamond.security.ResponseHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

/**
 * 移动端扫描验证码登录配置
 */
public class BaseQrCodeConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<BaseQrCodeConfigurer<H>, H> {

    private Long expiration = 120L;

    private final ApplicationContext context;

    private ResponseHandler responseHandle;

    /**
     * 这种扩展写法参考了CsrfConfigurer，通过构造函数注入上下文
     *
     * @see HttpSecurity#csrf()
     */
    public BaseQrCodeConfigurer(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void init(H builder) throws Exception {
        super.init(builder);
        getResponseHandle();
    }

    @Override
    public void configure(H http) throws Exception {
        BaseQrCodeFilter filter = new BaseQrCodeFilter();
        filter.setResponseHandle(responseHandle);

        CommonCacheUtil commonCacheUtil = context.getBean("commonCacheUtil", CommonCacheUtil.class);
        filter.setCommonCacheUtil(commonCacheUtil);

        filter.setExpiration(this.expiration);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    public BaseQrCodeConfigurer<H> expiration(Long expiration) {
        this.expiration = expiration;
        return this;
    }

    /**
     * 设置成功或失败的策略接口
     *
     * @param handle
     */
    public BaseQrCodeConfigurer<H> responseHandle(ResponseHandler handle) {
        Assert.notNull(handle, "ResponseHandle cannot be null");
        this.responseHandle = handle;
        return this;
    }

    private ResponseHandler getResponseHandle() {
        if (this.responseHandle != null) {
            return this.responseHandle;
        }
        this.responseHandle = new BaseDefaultSecurityResponseHandler();
        return this.responseHandle;
    }
}
