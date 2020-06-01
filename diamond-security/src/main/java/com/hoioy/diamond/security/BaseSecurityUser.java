package com.hoioy.diamond.security;

import com.hoioy.diamond.common.dto.CommonUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 登录成功后缓存起来的用户对象，扩展增加了UserDto，方便后续获取当前登录用户
 */
public class BaseSecurityUser extends User {
    private CommonUserDTO userInfoDto;

    public BaseSecurityUser(CommonUserDTO userInfoDto, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userInfoDto = userInfoDto;
    }

    public BaseSecurityUser(CommonUserDTO userInfoDto, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userInfoDto = userInfoDto;
    }

    public CommonUserDTO getUserInfoDto() {
        return userInfoDto;
    }
}
