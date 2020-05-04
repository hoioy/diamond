package com.hoioy.jiayin.seucrity;

import com.hoioy.diamond.common.dto.DiamondUserDTO;
import com.hoioy.diamond.security.DiamondUserDetailServiceImpl;
import com.hoioy.diamond.security.jwt.JwtAccessDeniedHandler;
import com.hoioy.diamond.security.jwt.JwtAuthenticationEntryPoint;
import com.hoioy.jiayin.conf.JiaYinJWT;
import com.hoioy.jiayin.seucrity.converter.MiniAppJwtAccessTokenConverter;
import com.hoioy.jiayin.seucrity.converter.MiniAppOauthServerJwtAccessTokenConverter;
import com.hoioy.jiayin.seucrity.filter.MiniAppAuthorizationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

//子类去继承
public abstract class MiniAppJwtWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    public abstract List<String> getRoleIdsByRequestUrl(String requestUrl);

    public abstract DiamondUserDTO getDiamondUserDTOByLoginName(String loginName);

    public abstract List<String> getRoleIdsByLoginName(String loginName);
    @Value("${jiayin.jwt.privateStr}")
    private String privateStr;
    @Value("${jiayin.jwt.publicStr}")
    private String publicStr;
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
    public MiniAppAuthorizationTokenFilter miniAppAuthorizationTokenFilter() throws Exception {
        return new MiniAppAuthorizationTokenFilter();
    }

    @Bean
    public MiniAppJwtAccessTokenConverter diamondJwtTokenUtil() {
        return new MiniAppJwtAccessTokenConverter(secret, expiration, refresh);
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

}
