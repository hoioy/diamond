package com.hoioy.diamond.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础异常类
 */
@Data
@NoArgsConstructor
public abstract class BaseException extends RuntimeException {
    private static final Long serialVersionUID = 1L;
    private String message;

    public BaseException(String template, Object... params) {
        super(String.format(template, params));
        this.message = String.format(template, params);
    }

    public BaseException(Throwable cause, String template, Object... params) {
        super(String.format(template, params), cause);
        this.message = String.format(template, params);
    }

    // 异常状态码，需要子类进行定义，并且子类间状态码不重复
    public abstract Integer getCode();
}
