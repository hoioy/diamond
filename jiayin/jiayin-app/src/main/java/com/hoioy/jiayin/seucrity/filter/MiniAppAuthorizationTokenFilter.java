package com.hoioy.jiayin.seucrity.filter;

import com.hoioy.diamond.security.jwt.JwtClaimDTO;
import com.hoioy.diamond.security.jwt.converter.DiamondJwtAccessTokenConverter;
import com.hoioy.diamond.security.jwt.converter.DiamondOauthServerJwtAccessTokenConverter;
import com.hoioy.jiayin.payload.MiniAppPayload;
import com.hoioy.jiayin.seucrity.WeChatToken;
import com.hoioy.jiayin.seucrity.converter.MiniAppJwtAccessTokenConverter;
import com.hoioy.jiayin.seucrity.converter.MiniAppOauthServerJwtAccessTokenConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class MiniAppAuthorizationTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String tokenHeader = "Authorization";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MiniAppJwtAccessTokenConverter miniAppJwtAccessTokenConverter;

    @Autowired
    private MiniAppOauthServerJwtAccessTokenConverter miniAppOauthServerJwtAccessTokenConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.debug("processing authentication for '{}'", request.getRequestURL());
        //先从parameter取token
        String authToken = extractToken(request);
        if (!StringUtils.isEmpty(authToken)) {
            MiniAppPayload miniAppPayload = convert(authToken);

            if (miniAppPayload != null
                    && StringUtils.isNotEmpty(miniAppPayload.getSubject())
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.debug("checking authentication for user '{}'", miniAppPayload.getSubject());
                Boolean isValid = false;
                if (miniAppPayload.getRefreshDate() == null) {
                    //Diamond-oauth-server的token没有refreshdate
                    if (!miniAppOauthServerJwtAccessTokenConverter.isInExpired(miniAppPayload)) {
                        //没有过期
                        isValid = true;
                    }
                } else if (miniAppJwtAccessTokenConverter.isInRefreshDate(miniAppPayload)) {
                    //token在可刷新时间范围内，有效
                    if (miniAppJwtAccessTokenConverter.isInExpired(miniAppPayload)) {
                        //token可刷新但是已经过期，需要刷新token
                        String newToken = miniAppJwtAccessTokenConverter.refreshToken(miniAppPayload);
                        response.addHeader(tokenHeader, newToken);
                        logger.debug("new token '{}'", newToken);
                    }
                    isValid = true;
                }

                if (isValid) {
                    //写小程序自定义逻辑 拿到用户权限

                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(miniAppPayload.getOpenid());
                    WeChatToken authentication =
                            new WeChatToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authorizated user '{}', setting security context", miniAppPayload.getOpenid());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                logger.warn("need token");
//            throw new UsernameNotFoundException("没有收到token");
            }
        }

        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authToken = request.getParameter("token");
        if (StringUtils.isEmpty(authToken)) {
            //如果parameter中没有，再从header中取
            final String requestHeader = request.getHeader(tokenHeader);
            if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
                authToken = requestHeader.substring(7);
            } else {
                logger.warn("couldn't find bearer string, will ignore the header");
            }
        }
        return authToken;
    }


    private MiniAppPayload convert(String token) {
        MiniAppPayload MiniAppPayload = null;
        try {
            MiniAppPayload = miniAppJwtAccessTokenConverter.convert(token);
        } catch (IllegalArgumentException e) {
            logger.error("an error occured during diamondJwtAccessTokenConverter convert from token", e);
        } catch (IOException e) {
            logger.error("an error occured during diamondJwtAccessTokenConverter convert from token", e);
        }

        if(MiniAppPayload == null){
            //如果为空，继续使用Diamond-oquth-server的token解析
            try {
                MiniAppPayload = miniAppOauthServerJwtAccessTokenConverter.convert(token);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during diamondOauthServerJwtAccessTokenConverter convert from token", e);
            }  catch (JwtValidationException e){
                logger.error("an JwtValidationException occured during diamondOauthServerJwtAccessTokenConverter convert from token", e);
            }
        }
        return MiniAppPayload;
    }
}
