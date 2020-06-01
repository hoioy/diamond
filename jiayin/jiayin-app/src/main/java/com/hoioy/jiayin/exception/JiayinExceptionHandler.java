package com.hoioy.jiayin.exception;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 框架统一异常
 * <p>
 * servlet中的错误状态
 * javax.servlet.error.status_code             类型为Integer        错误状态代码
 * javax.servlet.error.exception_type          类型为Class          异常的类型
 * javax.servlet.error.message                 类型为String         异常的信息
 * javax.servlet.error.exception               类型为Throwable      异常类
 * javax.servlet.error.request_uri             类型为String         异常出现的页面
 * javax.servlet.error.servlet_name            类型为String         异常出现的servlet名
 * <p>
 */
@ControllerAdvice(basePackages = {"com.hoioy.jiayin"})
public class JiayinExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(JiayinExceptionHandler.class);
    //diamond base的基础异常，如果实际项目没有覆盖，则会被此handler捕捉处理
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity<?> baseExceptionHandler(HttpServletRequest request, BaseException ex) {
        HttpStatus status = getStatus(request);
        //TODO 自定义扩展HTTP status，或不扩展, 与熔断机制融合
        // 为500时，返回自定义错误码
        ex.printStackTrace();
        return new ResponseEntity<>(new ResultDTO<>(ex.getCode(), ex.getMessage(), ex.getMessage()), HttpStatus.OK);
    }

    //最基础异常封装，起"保底"作用
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> runtimeExceptionHandler(HttpServletRequest request, Exception ex) {
        HttpStatus status = getStatus(request);
        ex.printStackTrace();
        return new ResponseEntity<>(new ResultDTO<>(status.value(), ex.getMessage(), ex.getMessage()), status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}


