package com.hoioy.diamond.security.jwt.configurer;

import com.hoioy.diamond.security.jwt.converter.BaseJwtTokenAuthenticationConverter;
import com.hoioy.diamond.security.jwt.filter.BaseJwtTokenAuthenticationFilter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

/**
 * 统一处理token的刷新和校验 配置
 */
public class BaseJwtTokenAuthenticationConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<BaseJwtTokenAuthenticationConfigurer<H>, H> {

    private Converter<String, Authentication> converter = new BaseJwtTokenAuthenticationConverter();

    @Override
    public void configure(H http) throws Exception {
        BaseJwtTokenAuthenticationFilter filter = new BaseJwtTokenAuthenticationFilter();
        filter.setConverter(converter);
        http.addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }

    public BaseJwtTokenAuthenticationConfigurer<H> converter(Converter converter) {
        Assert.notNull(converter, "converter cannot be null");
        this.converter = converter;
        return this;
    }
}
