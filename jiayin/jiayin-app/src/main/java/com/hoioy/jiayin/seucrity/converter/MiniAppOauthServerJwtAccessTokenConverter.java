package com.hoioy.jiayin.seucrity.converter;

import com.hoioy.diamond.security.jwt.JwtClaimDTO;
import com.hoioy.jiayin.conf.JiaYinJWT;
import com.hoioy.jiayin.payload.MiniAppPayload;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * Diamond-oauth-server的token的转换器
 * 其他转换器
 * Created by iandtop on 2019/1/8.
 */
@Component
public class MiniAppOauthServerJwtAccessTokenConverter extends AbstractCustomJwtAccessTokenConverter {
    @Autowired
    private JiaYinJWT jiaYinJWT;

    final String EXP = "exp";

    public MiniAppOauthServerJwtAccessTokenConverter() {
    }

    public MiniAppOauthServerJwtAccessTokenConverter(JiaYinJWT jiaYinJWT) {
        this.jiaYinJWT = jiaYinJWT;
    }

    @Override
    public MiniAppPayload convert(String token) throws JwtValidationException {
        MiniAppPayload decrypt = jiaYinJWT.Decrypt(token);
        return decrypt;
    }
}
