package com.hoioy.diamond.common.util;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * spring security工具类，获取当前登录用户等等
 */
public class TDFSecurityUtils {
    /**
     * 获取当前登录用户名
     */
    public static String getCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails springSecurityUser = null;
        String loginName = null;

        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                springSecurityUser = (UserDetails) authentication.getPrincipal();
                loginName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                loginName = (String) authentication.getPrincipal();
            } else if (authentication instanceof AbstractAuthenticationToken) {
                loginName = authentication.getName();
            }
        }

        return loginName;
    }

    /**
     * 当前用户是否是已登录用户
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        final Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();

        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(TDFStatic.Security_ANONYMOUS)) {
                    return false;
                }
            }
        }

        return true;
    }
}