package com.hoioy.jiayin.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Data
public class JiaYinJWT {
    @Value("${ jiayin.jwt.privateStr}")
    private String privateStr;
    @Value("${ jiayin.jwt.publicStr}")
    private String publicStr;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public JiaYinJWT() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec   priPKCS8 = new PKCS8EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(privateStr));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey = keyFactory.generatePrivate(priPKCS8);

        X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(publicStr));
        // 取公钥匙对象
        publicKey = keyFactory.generatePublic(bobPubKeySpec);
    }
}
