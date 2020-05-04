package com.hoioy.jiayin.conf;

import cn.hutool.core.bean.BeanUtil;
import com.hoioy.jiayin.payload.MiniAppPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

@Data
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class JiaYinJWT {
    private JwtProperties jwtProperties;
    private PrivateKey privateKey;
    private PublicKey publicKey;



    @Autowired
    public JiaYinJWT(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        try {
            PKCS8EncodedKeySpec   priPKCS8 = new PKCS8EncodedKeySpec(
                    new BASE64Decoder().decodeBuffer(jwtProperties.getPrivateStr()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = keyFactory.generatePrivate(priPKCS8);

            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
                    new BASE64Decoder().decodeBuffer(jwtProperties.getPublicStr()));
            // 取公钥匙对象
            publicKey = keyFactory.generatePublic(bobPubKeySpec);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public JiaYinJWT() {

    }


    public MiniAppPayload Decrypt(String token) {
        PublicKey publicKey = this.getPublicKey();

        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token);
        Map body = jws.getBody();
        MiniAppPayload miniAppPayload = BeanUtil.mapToBean(body, MiniAppPayload.class, false);

        return miniAppPayload;
    }


    public String encryption(MiniAppPayload miniAppPayload) {
        PrivateKey privateKey = this.getPrivateKey();
        String jws = Jwts.builder()
                .signWith(privateKey)
                .claim("sessionKey", miniAppPayload.getSessionKey())
                .claim("openid", miniAppPayload.getOpenid())
                .claim("unionid", miniAppPayload.getUnionid())
                .setSubject("jiayin")
                .setAudience("chenzhe")
                .setIssuedAt(miniAppPayload.getIssuedAt())
//                .setExpiration(miniAppPayload.getExpirationDate())
                .compact();

        return jws;
    }
}
