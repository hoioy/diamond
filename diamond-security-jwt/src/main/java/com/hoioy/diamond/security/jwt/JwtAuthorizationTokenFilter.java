package com.hoioy.diamond.security.jwt;

import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonSecurityUtils;
import com.hoioy.diamond.security.jwt.converter.BaseJwtAccessTokenConverter;
import com.hoioy.diamond.security.jwt.converter.BaseOauthServerJwtAccessTokenConverter;
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
    private BaseJwtAccessTokenConverter baseJwtAccessTokenConverter;

    @Autowired
    private BaseOauthServerJwtAccessTokenConverter baseOauthServerJwtAccessTokenConverter;

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
                    if (!baseOauthServerJwtAccessTokenConverter.isInExpired(jwtClaimDTO)) {
                        //没有过期
                        isValid = true;
                    }
                } else if (baseJwtAccessTokenConverter.isInRefreshDate(jwtClaimDTO)) {
                    //token在可刷新时间范围内，有效
                    if (baseJwtAccessTokenConverter.isInExpired(jwtClaimDTO)) {
                        //token可刷新但是已经过期，需要刷新token
                        String newToken = baseJwtAccessTokenConverter.refreshToken(jwtClaimDTO);
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
        if (CommonSecurityUtils.isBaseJWTToken(token)) {
            //如果是框架自定生成的token
            try {
                jwtClaimDTO = baseJwtAccessTokenConverter.convert(token);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during baseJwtAccessTokenConverter convert from token", e);
            } catch (IOException e) {
                logger.error("an error occured during baseJwtAccessTokenConverter convert from token", e);
            } catch (BaseException e) {
                logger.error("an error occured during baseJwtAccessTokenConverter convert from token", e);
            }
        } else {
            //如果是OAuth2 Server生成的token
            try {
                jwtClaimDTO = baseOauthServerJwtAccessTokenConverter.convert(token);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during baseOauthServerJwtAccessTokenConverter convert from token", e);
            } catch (IOException e) {
                logger.error("an error occured during baseOauthServerJwtAccessTokenConverter convert from token", e);
            } catch (InvocationTargetException e) {
                logger.error("an error occured during baseOauthServerJwtAccessTokenConverter convert from token", e);
            } catch (IllegalAccessException e) {
                logger.error("an error occured during baseOauthServerJwtAccessTokenConverter convert from token", e);
            } catch (JwtValidationException e) {
                logger.error("an JwtValidationException occured during baseOauthServerJwtAccessTokenConverter convert from token", e);
            }
        }

        return jwtClaimDTO;
    }
}
