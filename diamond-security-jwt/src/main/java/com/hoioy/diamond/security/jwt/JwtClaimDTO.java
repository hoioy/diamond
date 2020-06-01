package com.hoioy.diamond.security.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TODO 按需扩展
 * JWT paload中的对象
 * Created by iandtop on 2018/10/26.
 */
@Data
@NoArgsConstructor
public class JwtClaimDTO {
    private String subject;
    //创建时间
    private Date createdDate;
    //过期时间
    private Date expirationDate;
    //最近一次刷新时间
    private Date IssuedAt;
    //可更新截止时间
    private Date refreshDate;

    //以下是spring security oauth2的属性，参考：AccessTokenConverter
    //token的客户
    private String aud;
    //oauth2协议的client id
    private String client_id;
    //经常使用的，以数字时间定义失效期，也就是当前时间以后的某个时间本token失效。
    private Long exp;
    //JWT唯一标识. 能用于防止 JWT重复使用，一次只用一个token
    private String jti;
}
