package com.hoioy.diamond.log.exception;

import com.hoioy.diamond.common.exception.BaseException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TDFLogException extends BaseException {
    private static final Long serialVersionUID = 1L;
    // code为异常状态码，用户需要注意状态码的分配不重复（重复后容易产生混淆）
    // 建议使用http状态码后面补充两位数字的格式进行定义
    private static final Integer code = 42001;

    public TDFLogException(String template, Object... params) {
        super(template, params);
    }

    public TDFLogException(Throwable cause, String template, Object... params) {
        super(cause, template, params);
    }

    @Override
    public Integer getCode() {
        return code;
    }
}