package com.hoioy.diamond.common.exception;

import com.hoioy.diamond.common.dto.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final String ERROR_MES_SEPARATOR = "####";
    private final String message = "服务器开小差了";


    /**
     * 基础异常，如果实际项目没有覆盖，则会被此handler捕捉处理
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity<ResultDTO> baseExceptionHandler(HttpServletRequest request, BaseException ex) {
        // 框架自定义异常属于业务异常，统一返回200状态
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ResultDTO<>(ex.getCode(),ex.getMessage(), ex.getMessage(), ""), HttpStatus.OK);
    }

    /**
     * 唯一索引异常捕获
     * @param ex
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResultDTO> dataIntegrityViolationExceptionExceptionHandler(DataIntegrityViolationException ex) {
        // 框架自定义异常属于业务异常，统一返回200状态
        logger.error(ex.getMessage(),ex);
        Throwable cause = ex.getCause();
        String errorMessage = "";
        if (cause instanceof SQLIntegrityConstraintViolationException) {
            errorMessage = cause.getMessage();
        }
        if (!(cause instanceof SQLIntegrityConstraintViolationException)) {
            errorMessage = cause.getCause().getMessage();
        }
        //匹配汉字正则
        String reg = "[u4E00-u9FA5]";
        //匹配英文字母和单引号正则
        String rega = "[a-zA-Z_\']";

        Pattern compile = Pattern.compile(reg);
        Matcher matcher = compile.matcher(errorMessage);
        String msg = "";
        //如果包含中文
        if (matcher.find()) {
            msg = errorMessage;
            //将非中文字符替换为空
            msg = msg.replaceAll(rega,"").trim()+"重复";
            return new ResponseEntity<>(new ResultDTO<>(40001,msg,errorMessage , ""), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResultDTO<>(40001,message,errorMessage , ""), HttpStatus.OK);
        }
    }

    /**
     * 通用方法参数校验异常捕获
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error(ex.getMessage(), ex);
        StringBuilder erroeMessage = new StringBuilder(" ");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            erroeMessage.append(((FieldError) error).getField()+":"+error.getDefaultMessage()+" ") ;
        });
        return new ResponseEntity<>(new ResultDTO<>(400, message,erroeMessage.toString(), ""), HttpStatus.OK);
    }

    /**
     * 通用方法参数校验异常捕获
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ResultDTO> handleValidationExceptions(ValidationException ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ResultDTO<>(400, message,ex.getMessage(), ""), HttpStatus.OK);
    }

    /**
     * 最基础异常封装，起"保底"作用
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResultDTO> runtimeExceptionHandler(HttpServletRequest request, Exception ex) {
        HttpStatus status = getStatus(request);
        String messageDetail = ex.getMessage();

        String msg = message;
        //TODO 判断是否包含友好的描述信息。判断是否包含中文，如果包含中文，则拼接到message中
        Matcher m = Pattern.compile("[\u4e00-\u9fa5]").matcher(messageDetail);
        if (m.find()) {
            msg = messageDetail;
        }
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ResultDTO<>(status.value(), msg , messageDetail, ""), status);
    }

    public HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}


