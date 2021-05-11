package com.hoioy.diamond.security;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BasePasswordEncoder extends BCryptPasswordEncoder {
    @Value("${base.security.password.enableSM4:false}")
    private Boolean enableSM4;

    @Value("${base.security.password.SM4SecretKey:SM4SecretKey0000}")
    private String SM4SecretKey;

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (enableSM4) {
            //是否用国密sm4解密
            SymmetricCrypto sm4 = SmUtil.sm4(SM4SecretKey.getBytes());
            rawPassword = sm4.decryptStr(rawPassword.toString(), CharsetUtil.CHARSET_UTF_8);
        }
        return super.matches(rawPassword, encodedPassword);
    }
}
