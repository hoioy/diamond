package com.hoioy.diamond.common.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 框架自己使用的异常处理类
 */
@RestControllerAdvice(basePackages = {"com.hoioy.diamond"})
@ConditionalOnClass(javax.servlet.Servlet.class)
@Order(Ordered.LOWEST_PRECEDENCE)
final class CommonExceptionHandler extends BaseExceptionHandler {

}


