package com.hoioy.diamond.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 统一接口返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO<T> implements Serializable {

    private static final long serialVersionUID = 2783377098145240357L;
    private Integer code = HttpStatus.OK.value();
    private String message = "操作成功";
    private String errorMessage = "";
    private T data;

    public ResultDTO(T data) {
        this.data = data;
    }

    public ResultDTO(Integer code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public ResultDTO(Integer code, String errorMessage, T data) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.data = data;
    }
}
