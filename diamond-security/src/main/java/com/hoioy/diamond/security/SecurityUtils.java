package com.hoioy.diamond.security;

import com.hoioy.diamond.common.util.TDFSecurityUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Diamond Security扩展工具类
 */
public final class SecurityUtils extends TDFSecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     */
    public static Object getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            if (authentication.getPrincipal() instanceof TaijiUser) {
                TaijiUser springSecurityUser = (TaijiUser) authentication.getPrincipal();
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