package com.hoioy.jiayin.seucrity.converter;

import com.hoioy.diamond.security.jwt.JwtClaimDTO;
import com.hoioy.jiayin.payload.MiniAppPayload;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * Created by iandtop on 2019/1/9.
 */
public abstract class AbstractCustomJwtAccessTokenConverter {
    public abstract MiniAppPayload convert(String token) throws IOException, InvocationTargetException, IllegalAccessException;

    //是否过期，超过expirationDate
    public Boolean isInExpired(MiniAppPayload payload) throws IOException {
        final Date expiration = payload.getExpirationDate();
        return expiration.before(new Date());
    }

    //是否在可刷新时间内
    public Boolean isInRefreshDate(MiniAppPayload payload) throws IOException {
        final Date refreshDate = payload.getRefreshDate();
        return refreshDate.after(new Date());
    }

}
