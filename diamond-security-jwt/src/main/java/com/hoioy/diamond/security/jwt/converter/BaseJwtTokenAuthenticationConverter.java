package com.hoioy.diamond.security.jwt.converter;

import com.hoioy.diamond.security.BaseAuthenticationToken;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/3/25
 * @describe
 */
public class BaseJwtTokenAuthenticationConverter implements Converter<String, Authentication> {
    private BaseJwtTokenConverter jwtTokenConverter = new BaseJwtTokenConverter();

    @Override
    public Authentication convert(String bearerToken) {
        JWTClaimsSet jwtClaimsSet = jwtTokenConverter.verifyRS256Token(bearerToken);
        if (jwtClaimsSet == null) {
            return null;
        }
        Set<GrantedAuthority> grantedAuthorities = obtionGrantedAuthorities(jwtClaimsSet);

        Map<String, Object> claims = jwtClaimsSet.getClaims();

        String userName = jwtClaimsSet.getSubject();

        BaseAuthenticationToken authenticationToken = new BaseAuthenticationToken(userName,claims,grantedAuthorities);

        return authenticationToken;
    }

    // 取得用户的权限
    private Set<GrantedAuthority> obtionGrantedAuthorities(JWTClaimsSet jwtClaimsSet ) {
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        String authorities = (String) jwtClaimsSet.getClaim("authorities");
        String[] split = authorities.split(",");
        for (String roleId : split) {
            authSet.add(new SimpleGrantedAuthority(roleId));
        }
        return authSet;
    }

    public BaseJwtTokenConverter getJwtTokenConverter() {
        return jwtTokenConverter;
    }

    public void setJwtTokenConverter(BaseJwtTokenConverter jwtTokenConverter) {
        this.jwtTokenConverter = jwtTokenConverter;
    }
}
