package com.hoioy.diamond.security.jwt;

import com.hoioy.diamond.security.jwt.converter.DiamondJwtAccessTokenConverter;
import com.hoioy.diamond.security.jwt.converter.DiamondOauthServerJwtAccessTokenConverter;
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

public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String tokenHeader = "Authorization";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DiamondJwtAccessTokenConverter diamondJwtAccessTokenConverter;

    @Autowired
    private DiamondOauthServerJwtAccessTokenConverter diamondOauthServerJwtAccessTokenConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.debug("processing authentication for '{}'", request.getRequestURL());
        //先从parameter取token
        String authToken = extractToken(request);
        if (!StringUtils.isEmpty(authToken)) {
            JwtClaimDTO jwtClaimDTO = convert(authToken);

            if (jwtClaimDTO != null
                    && StringUtils.isNotEmpty(jwtClaimDTO.getSubject())
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.debug("checking authentication for user '{}'", jwtClaimDTO.getSubject());
                Boolean isValid = false;
                if (jwtClaimDTO.getRefreshDate() == null) {
                    //Diamond-oauth-server的token没有refreshdate
                    if (!diamondOauthServerJwtAccessTokenConverter.isInExpired(jwtClaimDTO)) {
                        //没有过期
                        isValid = true;
                    }
                } else if (diamondJwtAccessTokenConverter.isInRefreshDate(jwtClaimDTO)) {
                    //token在可刷新时间范围内，有效
                    if (diamondJwtAccessTokenConverter.isInExpired(jwtClaimDTO)) {
                        //token可刷新但是已经过期，需要刷新token
                        String newToken = diamondJwtAccessTokenConverter.refreshToken(jwtClaimDTO);
                        response.addHeader(tokenHeader, newToken);
                        logger.debug("new token '{}'", newToken);
                    }
                    isValid = true;
                }

                if (isValid) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtClaimDTO.getSubject());
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authorizated user '{}', setting security context", jwtClaimDTO.getSubject());
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


    private JwtClaimDTO convert(String token) {
        JwtClaimDTO jwtClaimDTO = null;
        try {
            jwtClaimDTO = diamondJwtAccessTokenConverter.convert(token);
        } catch (IllegalArgumentException e) {
            logger.error("an error occured during diamondJwtAccessTokenConverter convert from token", e);
        } catch (IOException e) {
            logger.error("an error occured during diamondJwtAccessTokenConverter convert from token", e);
        }

        if(jwtClaimDTO == null){
            //如果为空，继续使用Diamond-oquth-server的token解析
            try {
                jwtClaimDTO = diamondOauthServerJwtAccessTokenConverter.convert(token);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during diamondOauthServerJwtAccessTokenConverter convert from token", e);
            } catch (IOException e) {
                logger.error("an error occured during diamondOauthServerJwtAccessTokenConverter convert from token", e);
            } catch (InvocationTargetException e) {
                logger.error("an error occured during diamondOauthServerJwtAccessTokenConverter convert from token", e);
            } catch (IllegalAccessException e) {
                logger.error("an error occured during diamondOauthServerJwtAccessTokenConverter convert from token", e);
            } catch (JwtValidationException e){
                logger.error("an JwtValidationException occured during diamondOauthServerJwtAccessTokenConverter convert from token", e);
            }
        }
        return jwtClaimDTO;
    }
}
