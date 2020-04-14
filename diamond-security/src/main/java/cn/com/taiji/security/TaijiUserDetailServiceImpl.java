package com.hoioy.diamond.security;

import com.hoioy.diamond.common.dto.TDFUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 该类的主要作用是为Spring Security提供一个经过用户认证后的UserDetails。
 * 该UserDetails包括用户名、密码、是否可用、是否过期等信息。
 */
public abstract class TaijiUserDetailServiceImpl implements UserDetailsService {
    public abstract TDFUserDTO findTDFUserDTOByLoginName(String loginName);

    public abstract List<String> findRoleIdsByLoginName(String loginName);

    // 登录验证
    @Override
    public UserDetails loadUserByUsername(String loginName)
            throws UsernameNotFoundException {
        /** 连接数据库根据登陆？？用户名称获得用户信息 */
        TDFUserDTO user = findTDFUserDTOByLoginName(loginName);
        if (user == null) {
            throw new UsernameNotFoundException(loginName);
        }
        if (!"1".equals(user.getState())) {
            throw new UsernameNotFoundException("该用户处于锁定状态");
        }

        Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(loginName);

        boolean enables = true; // 是否可用
        boolean accountNonExpired = true; // 是否过期
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        // 封装成spring security的user
        TaijiUser userdetail = new TaijiUser(user, user.getLoginName(), user.getPassword(),
                enables, accountNonExpired, credentialsNonExpired,
                accountNonLocked, grantedAuths);
        return userdetail;
    }

    // 取得用户的权限
    private Set<GrantedAuthority> obtionGrantedAuthorities(String loginName) {
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        // 获取用户角色
        List<String> roleIds = findRoleIdsByLoginName(loginName);
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                authSet.add(new SimpleGrantedAuthority(roleId));
            });
        }
        return authSet;
    }
}
