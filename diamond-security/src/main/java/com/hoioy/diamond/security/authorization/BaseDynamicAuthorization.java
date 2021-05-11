package com.hoioy.diamond.security.authorization;

import com.hoioy.diamond.common.dto.CommonUserDTO;
import com.hoioy.diamond.security.BaseSecurityUser;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TDF Security动态鉴权实现类
 */
public abstract class BaseDynamicAuthorization {
    /**
     * 是否是白名单机制，如果某个请求没有配置权限时判断为`通过`还是`禁止`，
     * 果是`通过`则为白名单机制，否则是黑名单机制
     */
    @Value("${base.security.access.isWhiteLogic:true}")
    public Boolean isWhiteLogic;

    /**
     * @param request        当前请求
     * @param authentication 认证对象
     * @return 当前用户(authentication)是否有权限访问此资源(request)
     */
    public Boolean dynamicAccess(HttpServletRequest request, Authentication authentication) {
        // 先判断当前的用户有没有认证过
        if (!isAuthenticated(authentication)) {
            return false;
        }

        //判断用户是否被锁定
        if (authentication.getPrincipal() instanceof BaseSecurityUser) {
            CommonUserDTO userInfoDto = ((BaseSecurityUser) authentication.getPrincipal()).getUserInfoDto();
            if (userInfoDto != null && "0".equals(userInfoDto.getState())) {
                throw new AccessDeniedException("用户被锁定");
            }
        }

        Set<String> requiredAuthoritySet = getRequiredAuthoritySet(request);
        if (CollectionUtil.isEmpty(requiredAuthoritySet)) {
            //白名单机制 or 默认黑名单机制
            //白名单机制时候如果权限列表为null则认为默认拥有访问权限,返回true
            return isWhiteLogic;
        }

        //获取到当前用户对应的角色Role集合
        Set<String> userAuthoritySet = getUserAuthoritySet(authentication);

        // requiredAuthoritySet 和 userAuthoritySet 存在交集时，则认为用户拥有访问此资源的权限
        return CollectionUtil.isNotEmpty(CollectionUtil.intersection(requiredAuthoritySet, userAuthoritySet));
    }

    /**
     * 判断当前的用户有没有认证过
     */
    protected Boolean isAuthenticated(Authentication authentication) {
        // 获取到当前用户
        Object principal = authentication.getPrincipal();
        return principal != null && !"anonymousUser".equals(principal);
    }

    /**
     * 获取拥有访问此资源（request)的资格的权限(string)集合
     */
    protected abstract Set<String> getRequiredAuthoritySet(HttpServletRequest request);

    /**
     * 获取当前用户(authentication)拥有的权限(string)集合
     */
    protected Set<String> getUserAuthoritySet(Authentication authentication) {
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return roles;
    }

}
