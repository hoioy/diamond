package com.hoioy.diamond.security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 资源源数据定义，将所有的资源和权限对应关系建立起来，即定义某一资源可以被哪些角色访问
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 */
public abstract class BaseSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    public abstract List<String> findRoleIdsByRequestUrl(String requestUrl);

    /* 保存资源和权限的对应关系 key-资源url value-权限 */
    private Map<String, Collection<ConfigAttribute>> resourceMap = null;

    public BaseSecurityMetadataSource() {

    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    // 返回所请求资源所需要的权限
    // 需要注意性能问题
    // Diamond解决方案是使用Spring Cache增加缓存
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequest().getRequestURI();
        List<String> roleIds = findRoleIdsByRequestUrl(requestUrl);
        Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
        for (String roleId : roleIds) {
            ConfigAttribute configAttribute = new org.springframework.security.access.SecurityConfig(roleId);
            configAttributes.add(configAttribute);
        }

        return configAttributes;
    }

    public boolean supports(Class<?> arg0) {
        return true;
    }
}
