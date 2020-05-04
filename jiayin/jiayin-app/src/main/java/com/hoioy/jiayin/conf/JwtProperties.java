package com.hoioy.jiayin.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@ConfigurationProperties(prefix = "jiayin.jwt")
public class JwtProperties {
    private String privateStr;

    private String publicStr;

}
