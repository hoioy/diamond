package com.hoioy.diamond.security;

import com.hoioy.diamond.security.captcha.CaptchaService;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 带验证码校验功能
 */
public class BaseCaptchaAuthenticationFilter extends OncePerRequestFilter {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CaptchaService captchaService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            if(request.getRequestURI().equals("/login")){
                Map paramMap = request.getParameterMap();
                if (MapUtils.isNotEmpty(paramMap)) {
                    AuthenticationRequest authenticationRequest = new AuthenticationRequest();
                    BeanUtils.populate(authenticationRequest,paramMap);
                    captchaService.checkCaptcha(authenticationRequest);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
