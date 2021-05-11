package com.hoioy.diamond.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonException extends BaseException {
    private static final Long serialVersionUID = 1L;

    /**
     * code为异常状态码，用户需要注意状态码的分配不重复（重复后容易产生混淆）
     * 建议使用http状态码后面补充两位数字的格式进行定义
     */
    private static final Integer CODE = 40003;

    public CommonException(String template, Object... params) {
        super(template, params);
    }

    public CommonException(Throwable cause, String template, Object... params) {
        super(cause, template, params);
    }

    @Override
    public Integer getCode() {
        return CODE;
    }
}
