package com.hoioy.diamond.common.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 校验结果类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidResult {

    /**
     * 是否有错误
     */
    private boolean hasErrors;

    /**
     * 错误信息
     */
    private List<ErrorMessage> errors = new ArrayList<>();

    /**
     * 获取所有验证信息
     *
     * @return 字符串形式
     */
    public String getErrorStr() {
        StringBuilder sb = new StringBuilder();
        for (ErrorMessage error1 : errors) {
            String propertyPath = error1.getPropertyPath();
            String message = error1.getMessage();
            sb.append(propertyPath).append(":").append(message).append(" ");
        }
        return sb.toString();
    }

    public void addError(String propertyName, String message) {
        this.errors.add(new ErrorMessage(propertyName, message));
    }
}