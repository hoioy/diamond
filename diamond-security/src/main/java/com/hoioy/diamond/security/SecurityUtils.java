package com.hoioy.diamond.security;

import com.hoioy.diamond.common.util.CommonSecurityUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security扩展工具类
 */
public final class SecurityUtils extends CommonSecurityUtils {

    private SecurityUtils() {
    }

    public static Object getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            if (authentication.getPrincipal() instanceof BaseSecurityUser) {
                BaseSecurityUser springSecurityUser = (BaseSecurityUser) authentication.getPrincipal();
                return springSecurityUser.getUserInfoDto();
            } else if (authentication.getPrincipal() instanceof String) {
                return (String) authentication.getPrincipal();
            } else if (authentication instanceof AbstractAuthenticationToken) {
                return authentication.getName();
            }
        }

        return null;
    }
}