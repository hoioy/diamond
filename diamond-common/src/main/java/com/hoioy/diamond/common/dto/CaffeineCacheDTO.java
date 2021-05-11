package com.hoioy.diamond.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CaffeineCacheDTO implements Serializable {
    private static final long serialVersionUID = 11L;

    //缓存过期时间，单位秒
    private Long expireSecond;

    //缓存数据
    private String value;

}
