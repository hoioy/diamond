package com.hoioy.diamond.security.annotation;

import com.hoioy.diamond.common.dto.CommonUserDTO;
import com.hoioy.diamond.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

//子类去继承
public abstract class BaseWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    public abstract List<String> getRoleIdsByRequestUrl(String requestUrl);

    public abstract CommonUserDTO getCommonUserDTOByLoginName(String loginName);

    public abstract List<String> getRoleIdsByLoginName(String loginName);

    @Bean
    public BaseAccessDeniedHandler baseAccessDeniedHandler() {
        BaseAccessDeniedHandler access = new BaseAccessDeniedHandler();
        access.setErrorPage("403");
        return access;
    }

    public BaseAccessDecisionManager baseAccessDecisionManager() {
        return new BaseAccessDecisionManager();
    }

    public BaseSecurityMetadataSource baseSecurityMetadataSource() {
        return new BaseSecurityMetadataSource() {

            @Override
            public List<String> findRoleIdsByRequestUrl(String requestUrl) {
                return getRoleIdsByRequestUrl(requestUrl);
            }
        };
    }

    public BaseUserDetailServiceImpl baseUserDetailServiceImpl() {
        return new BaseUserDetailServiceImpl() {

            @Override
            public CommonUserDTO findCommonUserDTOByLoginName(String loginName) {
                return getCommonUserDTOByLoginName(loginName);
            }

            @Override
            public List<String> findRoleIdsByLoginName(String loginName) {
                return getRoleIdsByLoginName(loginName);
            }
        };
    }

    @Bean
    public BaseFilterSecurityInterceptor basefiltersecurityinterceptor() throws Exception {
        BaseFilterSecurityInterceptor basefiltersecurityinterceptor = new BaseFilterSecurityInterceptor();
        basefiltersecurityinterceptor.setFisMetadataSource(baseSecurityMetadataSource());
        basefiltersecurityinterceptor.setAccessDecisionManager(baseAccessDecisionManager());
        return basefiltersecurityinterceptor;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
