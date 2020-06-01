package com.hoioy.diamond.security.jwt.converter;

import com.hoioy.diamond.security.jwt.JwtClaimDTO;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * oauth2 server的token的转换器
 * 其他转换器
 * Created by iandtop on 2019/1/8.
 */
public class BaseOauthServerJwtAccessTokenConverter extends AbstractCustomJwtAccessTokenConverter {
    private JwtDecoder jwtDecoder;

    final String EXP = "exp";

    public BaseOauthServerJwtAccessTokenConverter() {
    }

    public BaseOauthServerJwtAccessTokenConverter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public JwtClaimDTO convert(String token) throws IOException, InvocationTargetException, IllegalAccessException, JwtValidationException {
        Jwt jwt = jwtDecoder.decode(token);
        Map<String, Object> claims = jwt.getClaims();

//        String claimsStr = jwt.getClaims();
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> claims = mapper.readValue(claimsStr, Map.class);
//        if (claims.containsKey(EXP) && claims.get(EXP) instanceof Integer) {
//            Integer intValue = (Integer) claims.get(EXP);
//            claims.put(EXP, new Long(intValue));
//        }

        JwtClaimDTO payload = new JwtClaimDTO();
        BeanUtils.populate(payload, claims);
        payload.setSubject((String) claims.get("user_name"));
//        payload.setExpirationDate(new Date(payload.getExp() * 1000));
        payload.setExpirationDate(Date.from((Instant) claims.get("exp")));
        return payload;
    }
}
