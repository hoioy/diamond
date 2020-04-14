package com.hoioy.diamond.security.annotation;

import com.hoioy.diamond.common.dto.TDFUserDTO;
import com.hoioy.diamond.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

//子类去继承
public abstract class TDFWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    public abstract List<String> getRoleIdsByRequestUrl(String requestUrl);

    public abstract TDFUserDTO getTDFUserDTOByLoginName(String loginName);

    public abstract List<String> getRoleIdsByLoginName(String loginName);

    @Bean
    public MyAccessDeniedHandler accessDeniedHandler() {
        MyAccessDeniedHandler access = new MyAccessDeniedHandler();
        access.setErrorPage("403");
        return access;
    }

    public TaijiAccessDecisionManager taijiAccessDecisionManager() {
        return new TaijiAccessDecisionManager();
    }

    public TaijiSecurityMetadataSource taijiSecurityMetadataSource() {
        return new TaijiSecurityMetadataSource() {

            @Override
            public List<String> findRoleIdsByRequestUrl(String requestUrl) {
                return getRoleIdsByRequestUrl(requestUrl);
            }
        };
    }

    public TaijiUserDetailServiceImpl taijiUserDetailServiceImpl() {
        return new TaijiUserDetailServiceImpl() {

            @Override
            public TDFUserDTO findTDFUserDTOByLoginName(String loginName) {
                return getTDFUserDTOByLoginName(loginName);
            }

            @Override
            public List<String> findRoleIdsByLoginName(String loginName) {
                return getRoleIdsByLoginName(loginName);
            }
        };
    }

    @Bean
    public TaijiFilterSecurityInterceptor taijifiltersecurityinterceptor() throws Exception {
        TaijiFilterSecurityInterceptor taijifiltersecurityinterceptor = new TaijiFilterSecurityInterceptor();
        taijifiltersecurityinterceptor.setFisMetadataSource(taijiSecurityMetadataSource());
        taijifiltersecurityinterceptor.setAccessDecisionManager(taijiAccessDecisionManager());
        return taijifiltersecurityinterceptor;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
