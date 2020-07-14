package com.hoioy.diamond.security.jwt.annotation;

import com.hoioy.diamond.common.dto.CommonUserDTO;
import com.hoioy.diamond.security.BaseAccessDecisionManager;
import com.hoioy.diamond.security.BaseSecurityMetadataSource;
import com.hoioy.diamond.security.BaseUserDetailServiceImpl;
import com.hoioy.diamond.security.jwt.JwtAccessDeniedHandler;
import com.hoioy.diamond.security.jwt.JwtAuthenticationEntryPoint;
import com.hoioy.diamond.security.jwt.JwtAuthorizationTokenFilter;
import com.hoioy.diamond.security.jwt.JwtBaseFilterSecurityInterceptor;
import com.hoioy.diamond.security.jwt.converter.BaseJwtAccessTokenConverter;
import com.hoioy.diamond.security.jwt.converter.BaseOauthServerJwtAccessTokenConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;

import java.util.List;

//子类去继承
public abstract class JwtWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    public abstract List<String> getRoleIdsByRequestUrl(String requestUrl);

    public abstract CommonUserDTO getCommonUserDTOByLoginName(String loginName);

    public abstract List<String> getRoleIdsByLoginName(String loginName);

    @Value("${tdf.security.jwt.secret}")
    private String secret = "mySecret";

    @Value("${tdf.security.jwt.jwkSetUri}")
    private String jwkSetUri;

    @Value("${tdf.security.jwt.expiration}")
    //单位：秒，默认配置1星期：3600*24*7=604800
    private Long expiration = 60L;

    @Value("${tdf.security.jwt.refresh}")
    //单位：秒，默认配置1星期：60*30
    private Long refresh = 1800L;

    @Bean
    public JwtAuthorizationTokenFilter baseJwtAuthorizationTokenFilter() throws Exception {
        return new JwtAuthorizationTokenFilter();
    }

    @Bean
    public BaseJwtAccessTokenConverter baseJwtTokenUtil() {
        return new BaseJwtAccessTokenConverter(secret, expiration, refresh);
    }

    @Bean
    public BaseOauthServerJwtAccessTokenConverter baseOauthServerJwtAccessTokenConverter() {
        return new BaseOauthServerJwtAccessTokenConverter(jwtDecoder());
    }

    public BaseAccessDecisionManager baseAccessDecisionManager() {
        return new BaseAccessDecisionManager();
    }

    public BaseSecurityMetadataSource baseSecurityMetadataSource() {
        BaseSecurityMetadataSource result = new BaseSecurityMetadataSource() {

            @Override
            public List<String> findRoleIdsByRequestUrl(String requestUrl) {
                return getRoleIdsByRequestUrl(requestUrl);
            }
        };
        return result;
    }

    @Bean
    public BaseUserDetailServiceImpl baseUserDetailService() {
        BaseUserDetailServiceImpl result = new BaseUserDetailServiceImpl() {

            @Override
            public CommonUserDTO findTDFUserDTOByLoginName(String loginName) {
                return getCommonUserDTOByLoginName(loginName);
            }

            @Override
            public List<String> findRoleIdsByLoginName(String loginName) {
                return getRoleIdsByLoginName(loginName);
            }
        };
        return result;
    }

    @Bean
    public JwtBaseFilterSecurityInterceptor basefiltersecurityinterceptor() throws Exception {
        JwtBaseFilterSecurityInterceptor basefiltersecurityinterceptor = new JwtBaseFilterSecurityInterceptor();
        basefiltersecurityinterceptor.setMetadataSource(baseSecurityMetadataSource());
        basefiltersecurityinterceptor.setAccessDecisionManager(baseAccessDecisionManager());
        return basefiltersecurityinterceptor;
    }

    @Bean
    public PasswordEncoder basePasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAccessDeniedHandler baseJwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }

    @Bean
    public JwtAuthenticationEntryPoint baseJwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    public JwtDecoder jwtDecoder() {
        return new NimbusJwtDecoderJwkSupport(jwkSetUri);
    }
}
