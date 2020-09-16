package com.hoioy.diamond.common.exception;

import com.hoioy.diamond.common.dto.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public abstract class BaseExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);
    //基础异常，如果实际项目没有覆盖，则会被此handler捕捉处理
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity<ResultDTO> baseExceptionHandler(HttpServletRequest request, BaseException ex) {
        // 框架自定义异常属于业务异常，统一返回200状态
        logger.error(ex.getMessage(),ex);
        return new ResponseEntity<>(new ResultDTO<>(ex.getCode(), ex.getMessage(), ex.getMessage()), HttpStatus.OK);
    }

    //最基础异常封装，起"保底"作用
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResultDTO> runtimeExceptionHandler(HttpServletRequest request, Exception ex) {
        HttpStatus status = getStatus(request);
        logger.error(ex.getMessage(),ex);
        return new ResponseEntity<>(new ResultDTO<>(status.value(), ex.getMessage(), ex.getMessage()), status);
    }

    public HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}



