package com.hoioy.diamond.security.jwt.converter;

import com.hoioy.diamond.security.jwt.JwtClaimDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import java.io.IOException;
import java.util.Date;

/**
 * 自定义token的转换
 * Created by iandtop on 2019/1/8.
 */
public class CustomJwtAccessTokenConverter extends AbstractCustomJwtAccessTokenConverter {
    private String secret = "mySecret";

    private Long expiration = 60L;

    private Long refresh = 1800L;

    public CustomJwtAccessTokenConverter() {
    }

    public CustomJwtAccessTokenConverter(String secret, Long expiration, Long refresh) {
        this.secret = secret;
        this.expiration = expiration;
        this.refresh = refresh;
    }

    //生成新的token
    public String generateToken(UserDetails userDetails) throws JsonProcessingException {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);
        final Date refreshDate = calculateRefreshDate(createdDate);

        JwtClaimDTO payload = new JwtClaimDTO();
        payload.setCreatedDate(createdDate);
        payload.setExpirationDate(expirationDate);
        payload.setSubject(userDetails.getUsername());
        payload.setRefreshDate(refreshDate);
        return doGenerateToken(payload);
    }

    //刷新token
    public String refreshToken(JwtClaimDTO payload) throws IOException {
        if (isInRefreshDate(payload)) {
            Date now = new Date();
            Date expirationDate = calculateExpirationDate(now);
            Date refreshDate = calculateRefreshDate(now);
            payload.setIssuedAt(now);
            payload.setExpirationDate(expirationDate);
            payload.setRefreshDate(refreshDate);
        }

        return doGenerateToken(payload);
    }

    public JwtClaimDTO convert(String token) throws IOException {
        token = CustomJwtDesUtil.decrypt(token, secret);
        Jwt jwt = JwtHelper.decode(token);
        ObjectMapper mapper = new ObjectMapper();
        JwtClaimDTO payload = mapper.readValue(jwt.getClaims(), JwtClaimDTO.class);
        return payload;
    }

    private String doGenerateToken(JwtClaimDTO payload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String mapJakcson = mapper.writeValueAsString(payload);
        MacSigner hmac = new MacSigner(secret.getBytes());
        Jwt jwt = JwtHelper.encode(mapJakcson, hmac);
        return CustomJwtDesUtil.encrypt(jwt.getEncoded(), secret);
    }

    protected Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

    protected Date calculateRefreshDate(Date createdDate) {
        return new Date(createdDate.getTime() + refresh * 1000);
    }

}
