package com.hoioy.diamond.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

public class BaseAccessDecisionManager implements AccessDecisionManager {

    /**
     * 解决了通过参数访问控制决策
     * @param authentication
     * @param obj
     * @param configAttributes
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    public void decide(Authentication authentication, Object obj,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null) {
            return;
        }
        //所请求的资源拥有的权限(一个资源对多个权限)
        Iterator<ConfigAttribute> it = configAttributes.iterator();
        while (it.hasNext()) {
            ConfigAttribute configAttribute = it.next();
            //访问所请求资源所需要的权限
            String needPermission = configAttribute.getAttribute();
            // 用户所拥有的权限authentication
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (ga.getAuthority().equals(needPermission)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException(" 没有权限访问！ ");
    }

    public boolean supports(ConfigAttribute configAttribute) {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return true;
    }


}