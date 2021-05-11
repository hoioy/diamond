package com.hoioy.diamond.security.jwt.filter;

import cn.hutool.core.util.StrUtil;
import com.hoioy.diamond.security.BaseConstants;
import com.hoioy.diamond.security.jwt.converter.BaseJwtTokenAuthenticationConverter;
import com.hoioy.diamond.security.jwt.converter.BaseJwtTokenConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 前后端分离场景下，统一处理token的刷新和校验
 * token的生成 @See JwtSuccessHandler
 */
public class BaseJwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/refreshToken");

    private Converter<String, Authentication> converter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //token校验逻辑
        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        //如果请求头为空,可能是非法请求,也可能是被permitAll的请求,在该filter不做安全考虑,统一进行放行,
        // 交给后续FilterSecurityInterceptor去判断该请求是否合法
        if (!StrUtil.startWithIgnoreCase(tokenHeader, BaseConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (requestMatcher.matches(request)) {
            //token主动刷新逻辑
            BaseJwtTokenAuthenticationConverter jwtConverter = (BaseJwtTokenAuthenticationConverter) converter;

            BaseJwtTokenConverter jwtTokenConverter = jwtConverter.getJwtTokenConverter();
            //刷新token
            String token = jwtTokenConverter.refreshRS256Token(tokenHeader);

            response.setHeader(HttpHeaders.AUTHORIZATION, BaseConstants.TOKEN_PREFIX + token);
            return;
        }


        //将token转换为Authentication对象
        Authentication authentication = converter.convert(tokenHeader);
        //如果不为null代表验证成功将security上下文进行填充
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //如果验证不成功交给其他过滤器进行验证或验证成功放行
        filterChain.doFilter(request, response);

    }

    public void setConverter(Converter<String, Authentication> converter) {
        this.converter = converter;
    }
}
