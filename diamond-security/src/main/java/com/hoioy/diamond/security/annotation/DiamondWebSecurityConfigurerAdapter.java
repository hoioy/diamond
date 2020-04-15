package com.hoioy.diamond.security.annotation;

import com.hoioy.diamond.common.dto.DiamondUserDTO;
import com.hoioy.diamond.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

//子类去继承
public abstract class DiamondWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    public abstract List<String> getRoleIdsByRequestUrl(String requestUrl);

    public abstract DiamondUserDTO getDiamondUserDTOByLoginName(String loginName);

    public abstract List<String> getRoleIdsByLoginName(String loginName);

    @Bean
    public DiamondAccessDeniedHandler accessDeniedHandler() {
        DiamondAccessDeniedHandler access = new DiamondAccessDeniedHandler();
        access.setErrorPage("403");
        return access;
    }

    public DiamondAccessDecisionManager diamondAccessDecisionManager() {
        return new DiamondAccessDecisionManager();
    }

    public DiamondSecurityMetadataSource diamondSecurityMetadataSource() {
        return new DiamondSecurityMetadataSource() {

            @Override
            public List<String> findRoleIdsByRequestUrl(String requestUrl) {
                return getRoleIdsByRequestUrl(requestUrl);
            }
        };
    }

    public DiamondUserDetailServiceImpl diamondUserDetailServiceImpl() {
        return new DiamondUserDetailServiceImpl() {

            @Override
            public DiamondUserDTO findDiamondUserDTOByLoginName(String loginName) {
                return getDiamondUserDTOByLoginName(loginName);
            }

            @Override
            public List<String> findRoleIdsByLoginName(String loginName) {
                return getRoleIdsByLoginName(loginName);
            }
        };
    }

    @Bean
    public DiamondFilterSecurityInterceptor diamondfiltersecurityinterceptor() throws Exception {
        DiamondFilterSecurityInterceptor diamondfiltersecurityinterceptor = new DiamondFilterSecurityInterceptor();
        diamondfiltersecurityinterceptor.setFisMetadataSource(diamondSecurityMetadataSource());
        diamondfiltersecurityinterceptor.setAccessDecisionManager(diamondAccessDecisionManager());
        return diamondfiltersecurityinterceptor;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
