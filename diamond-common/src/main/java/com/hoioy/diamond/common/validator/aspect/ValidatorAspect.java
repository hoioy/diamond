package com.hoioy.diamond.common.validator.aspect;

import com.hoioy.diamond.common.validator.ValidationUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Aspect
@Component
public class ValidatorAspect {
    /**
     * 声明切入点表达式
     */
    @Pointcut(value = "@annotation(valid)")
    public void declarePointCut(Valid valid) {}

    /**
     * 前置通知: 在目标方法(连接点)执行之前执行.
     */
    @Before("@annotation(valid)")
    public void  beforeMethod(JoinPoint joinPoint, Valid valid) throws IllegalAccessException, InstantiationException {
        //获取方法的参数
        Object [] args = joinPoint.getArgs();
        for (Object arg : args) {
            ValidationUtil.validate(arg);
        }
    }
}
