package com.hoioy.diamond.log.monitor;

import com.hoioy.diamond.common.service.CommonSecurityService;
import com.hoioy.diamond.log.annotation.OperationLogAnno;
import com.hoioy.diamond.log.service.IWebLogsService;
import cn.hutool.core.map.MapUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class WebMonitorLog {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //TODO zhaozhao这样定义变量不支持多线程，需要用Local符号修饰
    private LocalDateTime startTime; // 开始时间

    @Autowired
    private IWebLogsService iWebLogsService;

    @Value("${base.log.lock:on}")
    public String loglock;

    /**
     * 前置通知，在目标方法完成之后调用通知，此时不会关 心方法的输出是什么 方法调用前触发 -记录开始时间
     */
//    @Before("bean(*Controller)")
    @Before("within(com.hoioy.diamond.common.api.BaseController+)")
//    @Before("within(com.hoioy.diamond.*.web..*) || within(com.hoioy.diamond.*.api..*)")
    public void beforeAdvice(JoinPoint joinPoint) {
        startTime = LocalDateTime.now();
    }

    /**
     * 后置通知，在目标方法完成之后调用通知，此时不会关 心方法的输出是什么
     */
//    @After("bean(*Controller)")
    @After("within(com.hoioy.diamond.common.api.BaseController+)")
//    @After("within(com.hoioy.diamond.*.web..*) || within(com.hoioy.diamond.*.api..*)")
    public void afterAdvice(JoinPoint joinPoint) throws Exception {
        if (loglock.equals("on")) {
            iWebLogsService.saveLog(joinPoint, MapUtil.builder().put("startTime", startTime)
                    .put("currentLoginName", CommonSecurityService.instance.getCurrentLoginName()).build());
        }
    }

    /**
     * 返回通知，在目标方法成功执行之后调用，可以获得目标方法的返回值，但不能修改（修改也不影响方法的返回值）
     *
     * @param jp               接口，可以获得连接点的一些信息
     * @param retVal           目标方法返回值，和jp一样会由spring自动传入
     * @param operationLogAnno
     */
    @AfterReturning(returning = "retVal", pointcut = "@annotation(operationLogAnno)")
    public void afterReturningAdvice(JoinPoint jp, Object retVal, OperationLogAnno operationLogAnno) {
        // retVal = retVal + " (@AfterReturning 这个返回值只能读,但是不能改变这个值!)";
        // retVal = retVal + " (@AfterReturning)";
        // logger.info("AfterReturning--通知方法会在目标方法返回后调用; retVal = " + retVal);
        // logger.info(jp.toLongString());
        logger.info("-------执行" + operationLogAnno.desc() + "结束-------");
    }

    /**
     * 异常通知，在目标方法抛出异常后调用通知
     */
    @AfterThrowing(throwing = "ex", pointcut = "@annotation(operationLogAnno)")
    public void afterThrowingAdvice(JoinPoint joinPoint, OperationLogAnno operationLogAnno, Exception ex) {
        // logger.info("AfterThrowing--通知方法会在目标方法抛出异常后调用");
        logger.error("执行" + operationLogAnno.desc() + "异常", ex);
    }
}
