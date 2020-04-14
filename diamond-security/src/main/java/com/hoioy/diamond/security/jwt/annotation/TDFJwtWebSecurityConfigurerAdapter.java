package com.hoioy.diamond.security.jwt.annotation;

import com.hoioy.diamond.common.dto.TDFUserDTO;
import com.hoioy.diamond.security.TaijiAccessDecisionManager;
import com.hoioy.diamond.security.TaijiSecurityMetadataSource;
import com.hoioy.diamond.security.TaijiUserDetailServiceImpl;
import com.hoioy.diamond.security.jwt.JwtAccessDeniedHandler;
import com.hoioy.diamond.security.jwt.JwtAuthenticationEntryPoint;
import com.hoioy.diamond.security.jwt.JwtAuthorizationTokenFilter;
import com.hoioy.diamond.security.jwt.JwtTaijiFilterSecurityInterceptor;
import com.hoioy.diamond.security.jwt.converter.CustomJwtAccessTokenConverter;
import com.hoioy.diamond.security.jwt.converter.TDFOauthServerJwtAccessTokenConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;

import java.util.List;

//子类去继承
public abstract class TDFJwtWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    public abstract List<String> getRoleIdsByRequestUrl(String requestUrl);

    public abstract TDFUserDTO getTDFUserDTOByLoginName(String loginName);

    public abstract List<String> getRoleIdsByLoginName(String loginName);

    @Value("${diamond.security.jwt.secret}")
    private String secret = "mySecret";

    @Value("${diamond.security.jwt.jwkSetUri}")
    private String jwkSetUri;

    @Value("${diamond.security.jwt.expiration}")
    //单位：秒，默认配置1星期：3600*24*7=604800
    private Long expiration = 60L;

    @Value("${diamond.security.jwt.refresh}")
    //单位：秒，默认配置1星期：60*30
    private Long refresh = 1800L;

    @Bean
    public JwtAuthorizationTokenFilter tdfJwtAuthorizationTokenFilter() throws Exception {
        return new JwtAuthorizationTokenFilter();
    }

    @Bean
    public CustomJwtAccessTokenConverter tdfJwtTokenUtil() {
        return new CustomJwtAccessTokenConverter(secret, expiration, refresh);
    }

    @Bean
    public TDFOauthServerJwtAccessTokenConverter tdfOauthServerJwtAccessTokenConverter() {
        return new TDFOauthServerJwtAccessTokenConverter(jwtDecoder());
    }

    public TaijiAccessDecisionManager tdfAccessDecisionManager() {
        return new TaijiAccessDecisionManager();
    }

    public TaijiSecurityMetadataSource tdfSecurityMetadataSource() {
        TaijiSecurityMetadataSource result = new TaijiSecurityMetadataSource() {

            @Override
            public List<String> findRoleIdsByRequestUrl(String requestUrl) {
                return getRoleIdsByRequestUrl(requestUrl);
            }
        };
        return result;
    }

    @Bean
    public TaijiUserDetailServiceImpl tdfUserDetailService() {
        TaijiUserDetailServiceImpl result = new TaijiUserDetailServiceImpl() {

            @Override
            public TDFUserDTO findTDFUserDTOByLoginName(String loginName) {
                return getTDFUserDTOByLoginName(loginName);
            }

            @Override
            public List<String> findRoleIdsByLoginName(String loginName) {
                return getRoleIdsByLoginName(loginName);
            }
        };
        return result;
    }

    @Bean
    public JwtTaijiFilterSecurityInterceptor tdffiltersecurityinterceptor() throws Exception {
        JwtTaijiFilterSecurityInterceptor taijifiltersecurityinterceptor = new JwtTaijiFilterSecurityInterceptor();
        taijifiltersecurityinterceptor.setMetadataSource(tdfSecurityMetadataSource());
        taijifiltersecurityinterceptor.setAccessDecisionManager(tdfAccessDecisionManager());
        return taijifiltersecurityinterceptor;
    }

    @Bean
    public PasswordEncoder tdfPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAccessDeniedHandler tdfJwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }

    @Bean
    public JwtAuthenticationEntryPoint tdfJwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    public JwtDecoder jwtDecoder() {
        return new NimbusJwtDecoderJwkSupport(jwkSetUri);
    }
}
