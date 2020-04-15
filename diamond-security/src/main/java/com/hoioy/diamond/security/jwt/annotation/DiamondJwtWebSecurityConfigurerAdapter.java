package com.hoioy.diamond.security.jwt.annotation;

import com.hoioy.diamond.common.dto.DiamondUserDTO;
import com.hoioy.diamond.security.DiamondAccessDecisionManager;
import com.hoioy.diamond.security.DiamondSecurityMetadataSource;
import com.hoioy.diamond.security.DiamondUserDetailServiceImpl;
import com.hoioy.diamond.security.jwt.JwtAccessDeniedHandler;
import com.hoioy.diamond.security.jwt.JwtAuthenticationEntryPoint;
import com.hoioy.diamond.security.jwt.JwtAuthorizationTokenFilter;
import com.hoioy.diamond.security.jwt.DiamondJwtFilterSecurityInterceptor;
import com.hoioy.diamond.security.jwt.converter.DiamondJwtAccessTokenConverter;
import com.hoioy.diamond.security.jwt.converter.DiamondOauthServerJwtAccessTokenConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;

import java.util.List;

//子类去继承
public abstract class DiamondJwtWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    public abstract List<String> getRoleIdsByRequestUrl(String requestUrl);

    public abstract DiamondUserDTO getDiamondUserDTOByLoginName(String loginName);

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
    public JwtAuthorizationTokenFilter diamondJwtAuthorizationTokenFilter() throws Exception {
        return new JwtAuthorizationTokenFilter();
    }

    @Bean
    public DiamondJwtAccessTokenConverter diamondJwtTokenUtil() {
        return new DiamondJwtAccessTokenConverter(secret, expiration, refresh);
    }

    @Bean
    public DiamondOauthServerJwtAccessTokenConverter diamondOauthServerJwtAccessTokenConverter() {
        return new DiamondOauthServerJwtAccessTokenConverter(jwtDecoder());
    }

    public DiamondAccessDecisionManager diamondAccessDecisionManager() {
        return new DiamondAccessDecisionManager();
    }

    public DiamondSecurityMetadataSource diamondSecurityMetadataSource() {
        DiamondSecurityMetadataSource result = new DiamondSecurityMetadataSource() {

            @Override
            public List<String> findRoleIdsByRequestUrl(String requestUrl) {
                return getRoleIdsByRequestUrl(requestUrl);
            }
        };
        return result;
    }

    @Bean
    public DiamondUserDetailServiceImpl diamondUserDetailService() {
        DiamondUserDetailServiceImpl result = new DiamondUserDetailServiceImpl() {

            @Override
            public DiamondUserDTO findDiamondUserDTOByLoginName(String loginName) {
                return getDiamondUserDTOByLoginName(loginName);
            }

            @Override
            public List<String> findRoleIdsByLoginName(String loginName) {
                return getRoleIdsByLoginName(loginName);
            }
        };
        return result;
    }

    @Bean
    public DiamondJwtFilterSecurityInterceptor diamondfiltersecurityinterceptor() throws Exception {
        DiamondJwtFilterSecurityInterceptor diamondfiltersecurityinterceptor = new DiamondJwtFilterSecurityInterceptor();
        diamondfiltersecurityinterceptor.setMetadataSource(diamondSecurityMetadataSource());
        diamondfiltersecurityinterceptor.setAccessDecisionManager(diamondAccessDecisionManager());
        return diamondfiltersecurityinterceptor;
    }

    @Bean
    public PasswordEncoder diamondPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAccessDeniedHandler diamondJwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }

    @Bean
    public JwtAuthenticationEntryPoint diamondJwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    public JwtDecoder jwtDecoder() {
        return new NimbusJwtDecoderJwkSupport(jwkSetUri);
    }
}
