package com.hoioy.diamond.security.jwt.converter;

import com.hoioy.diamond.security.BaseConstants;
import com.hoioy.diamond.security.BaseSecurityUser;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.JWTClaimsSet;
import net.minidev.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/3/25
 * @describe
 */
public class BaseJwtTokenConverter {
    private String secret = "mySecret";

    //RSA私钥
    private String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC6vCb0jpf1cR8dNrtZP1OUf3YKG4z235E96uISS90WdoX0R63ErRSnHBGWVcFXFWSQLj2U1XINzxMnjG81RH8s5Z/nZdvOa1nxciliv/WPO0EQHnoCTJAg4Jkn3EMyHr+oVGQGe3OwNu32WU37v9Wqx5QM9SO8ZWOkkkrURApLNipBdwNvck5f+9iyshsCHKnSj7HN3Ado3+WMi4Urn0sYXtPySshrTSl3iZjmTuYEyWTRAWjlTdlylbVpUuVCCPsO/MeilK6QIBajwpbJbD6ci+D6WxvarPKm+YFvF1eLm1H3+sp9Pw5uODexpOYlu57VZZDh9p5QRz6EcIQiISuXAgMBAAECggEAf5JtNqR4Mkk/DXFH2vgFfKz7knox+rLQAjIGkqNbfq4oY7PYhkYMlwH5DfC2Lm1CD7JXheewjLiMiHdLvmN2UtDpRmfzG/mBMA9jMMCd3WWI1J9NkWaHDL8EQxQaoBIbs787uqX6akeMmMF4ImZzbcH9ZkDPUjUeNt9u2cdGFQzXuPT1G+F9zPNfHNDp7OEScTm3/kweKzTIIhIvrD/uBcoYir7spFlR0CgQHAkXegcM4c1CinOQ8INRWCCWB+SZ0g3NvrmgJvA1mluhwuXcXus3EXO8+6mxWeDdKIiAH954Whrq6ADECWDfzcWqGyYX6Y6/6BqxjWpoTm4Q/eJZ6QKBgQDpn9qGwYvxwZApNeiiQDKkYB8VcUZmUZcY3aYK3Jw6OFN8p/kOhihWexmFvQwi70EsleHba9CMBhqwUZAXDwT9trBQ1+Al+gtTItLi7tmNaslXCBzmcnR0J/XfzXl56fItDUwUToFetCe9Uc7//dU/beGpBsVP/eBiIPpLDyG29QKBgQDMnqJY6nJ7ewnQkbVBWpVUEeI1nSpm58yEWViK+SyHg5UKCfgfEZxhibB43hyKs2yb91gt2N8+tLMicHvAFFeRPeKJ2gm9aL4+3zV+FMsJ87Uuq5iHdcGjugxszwL902+Wl+fDW1ADr2a8FC56lWHCzyJIwWV/1jlt7EGj6QoI2wKBgH3uxD4FkKk1vL7qJ0dmsaW3hqnpUJiQ/JDT0dBjEPe1KxOz++XfXVkYrC5SNHuUWp5tAL1lhZolJDfND43Oc9NLqgk6BMKT4Yzj5aecNrsrR/LZFbMHGU0PyVLlkMit2fR9CXicxNHG7PD5a0rEijHRymVxl+TBpjVfL2xMcNENAoGBAMsBZ5hHoaUYgLhIl0drk1OBGqiOcQ7/UkzMR6g5ZhBcX3VCRsSsODYc4NRJqI+DS3HBh96Ul4gixsaYSm5awDX869BQXfFpBbZixTN9daM+Ard2zzE1hxPk9YbOKu2g48jIlMugwFWno4ldjG9Z3U/lKOJ93TFjXD7OcqEoByiZAoGBAMQ798x1gEKExQsxQLXg+i4tGcW+z0QQZiNQZsGG8JLS9GKgr1/LTQal8DfjGJ/IeLKbMZaYQReuwUyFx5NEw2qS5B3WwT7ftCQkgbaLWO/WCsuMisX3gcoayvXdJzz14u16/exrDnUGINtmnkSTi/PljEang6JAiH6DTf7w8IZI";
    //RSA 公钥
    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAurwm9I6X9XEfHTa7WT9TlH92ChuM9t+RPeriEkvdFnaF9EetxK0UpxwRllXBVxVkkC49lNVyDc8TJ4xvNUR/LOWf52XbzmtZ8XIpYr/1jztBEB56AkyQIOCZJ9xDMh6/qFRkBntzsDbt9llN+7/VqseUDPUjvGVjpJJK1EQKSzYqQXcDb3JOX/vYsrIbAhyp0o+xzdwHaN/ljIuFK59LGF7T8krIa00pd4mY5k7mBMlk0QFo5U3ZcpW1aVLlQgj7DvzHopSukCAWo8KWyWw+nIvg+lsb2qzypvmBbxdXi5tR9/rKfT8Objg3saTmJbue1WWQ4faeUEc+hHCEIiErlwIDAQAB";

    private Long expiration = 60L;

    private Long refresh = 1800L;

    public String generateToken(Authentication authentication) {
        BaseSecurityUser user = (BaseSecurityUser) authentication.getPrincipal();
        List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) authentication.getAuthorities();

        String authoritiesStr = "";
        for (int i = 0; i < authorities.size(); i++) {
            GrantedAuthority grantedAuthority = authorities.get(i);
            authoritiesStr += grantedAuthority.toString();
            if (i < authorities.size() - 1) {
                authoritiesStr += ",";
            }
        }
        String username = user.getUsername();
        final Date createdDate = new Date();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issueTime(createdDate)
                .issuer("diamond BASE")
                .expirationTime(new Date(createdDate.getTime() + expiration * 1000))
                .claim("refreshDate", createdDate.getTime() + refresh * 1000)
                .claim("authorities", authoritiesStr)
                .build();

        return doGenerateRS256Token(claimsSet.toJSONObject().toString());
    }

    /**
     * token验签，验签成功即代表该token合法
     *
     * @param bearerToken 包含bearer的token
     * @return
     */
    public JWTClaimsSet verifyRS256Token(String bearerToken) {
        String token = bearerToken.replace(BaseConstants.TOKEN_PREFIX, "").trim();
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(new Base64URL(publicKey).decode());
        JWTClaimsSet jwtClaimsSet = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

            JWSObject one = JWSObject.parse(token);

            one.verify(new RSASSAVerifier(publicKey));

            Payload onePayload = one.getPayload();

            JWSObject two = JWSObject.parse(onePayload.toString());

            Payload payload = two.getPayload();

            jwtClaimsSet = JWTClaimsSet.parse(payload.toJSONObject());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jwtClaimsSet;
    }

    /**
     * token验签，验签成功即代表该token合法
     *
     * @param token
     * @return
     */
    public Boolean verifyHS256Token(String token) {

        boolean verify = false;
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            verify = jwsObject.verify(new MACVerifier(secret));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return verify;
    }

    /**
     * 使用RSA256生成token
     *
     * @param payload token包含的数据
     * @return token
     */
    public String doGenerateRS256Token(String payload) {
        String token = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            //将私钥字符串转换为PKCS8EncodedKeySpec
            PKCS8EncodedKeySpec privateKetSpec = new PKCS8EncodedKeySpec(new Base64URL(privateKey).decode());

            //转换为PrivateKey
            PrivateKey privateKey = keyFactory.generatePrivate(privateKetSpec);

//            //获取jwtClaimsSet的json对象
//            String payload = jwtClaimsSet.toJSONObject().toJSONString();

            //设置算法为RS256
            JWSHeader header = new JWSHeader(JWSAlgorithm.RS256);

            JWSObject jwsObject = new JWSObject(header, new Payload(payload));

            //将jwtClaimsSet的json字符串签名加密
            jwsObject.sign(new RSASSASigner(privateKey));

            String serialize = jwsObject.serialize();

            //二次签名加密,防止破解
            JWSObject en = new JWSObject(header, new Payload(serialize));

            en.sign(new RSASSASigner(privateKey));

            token = en.serialize();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return token;
    }

    public String refreshRS256Token(String token) {
        JWTClaimsSet jwtClaimsSet = verifyRS256Token(token);
        String refreshToken = "";
        try {
            Date refreshDate = jwtClaimsSet.getDateClaim("refreshDate");
            if (refreshDate.after(new Date())) {
                JSONObject jsonObject = jwtClaimsSet.toJSONObject();

                //如果没有超过当前时间,基于当前时间续约
                jsonObject.put("refreshDate", new Date().getTime() + refresh * 1000);

                refreshToken = doGenerateRS256Token(jsonObject.toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return refreshToken;
    }

}
