package com.hoioy.diamond.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/3/16
 * @describe 用于扩展登陆方式中, 通用构造Authentication对象
 */
public class BaseAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal ;

    private Object credentials ;

    public BaseAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }


    public BaseAuthenticationToken(Object principal, Object credentials,
                                               Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
