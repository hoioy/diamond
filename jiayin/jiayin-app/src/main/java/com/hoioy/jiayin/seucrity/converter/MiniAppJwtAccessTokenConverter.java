package com.hoioy.jiayin.seucrity.converter;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hoioy.jiayin.conf.JiaYinJWT;
import com.hoioy.jiayin.payload.MiniAppPayload;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.Date;

/**
 * 自定义token的转换
 * Created by iandtop on 2019/1/8.
 */
@Component
public class MiniAppJwtAccessTokenConverter extends AbstractCustomJwtAccessTokenConverter {

    private JiaYinJWT jiaYinJWT;

    private String secret = "jiayin";

    private Long expiration = 60L;

    private Long refresh = 1800L;

    public MiniAppJwtAccessTokenConverter() {
    }
    @Autowired
    public MiniAppJwtAccessTokenConverter(JiaYinJWT jiaYinJWT) {
        this.jiaYinJWT = jiaYinJWT;
    }

    public MiniAppJwtAccessTokenConverter(String secret, Long expiration, Long refresh) {
        this.secret = secret;
        this.expiration = expiration;
        this.refresh = refresh;
    }

    //生成新的token
    public String generateToken(WxMaJscode2SessionResult wxMaJscode2SessionResult) throws JsonProcessingException {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);
        final Date refreshDate = calculateRefreshDate(createdDate);

        MiniAppPayload payload = new MiniAppPayload();
        payload.setCreatedDate(createdDate);
        payload.setExpirationDate(expirationDate);
        payload.setSubject("jiayin");
        payload.setOpenid(wxMaJscode2SessionResult.getOpenid());
        payload.setUnionid(wxMaJscode2SessionResult.getUnionid());
        payload.setSessionKey(wxMaJscode2SessionResult.getSessionKey());
        payload.setRefreshDate(refreshDate);
        return doGenerateToken(payload);
    }

    //刷新token
    public String refreshToken(MiniAppPayload payload) throws IOException {
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

    public MiniAppPayload convert(String token) throws IOException {
        MiniAppPayload decrypt = jiaYinJWT.Decrypt(token);
        return decrypt;
    }

    private String doGenerateToken(MiniAppPayload miniAppPayload)  {
        String token = jiaYinJWT.encryption(miniAppPayload);
        return token;
    }

    protected Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

    protected Date calculateRefreshDate(Date createdDate) {
        return new Date(createdDate.getTime() + refresh * 1000);
    }

}
