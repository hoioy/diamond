package com.hoioy.sample.log;

import com.hoioy.diamond.log.service.IWebLogsService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

//自定义日志切面。模仿 diamond-log模块中的 com.hoioy.diamond.log.monitor.WebMonitorLog，扩展或者修改diamond-log中默认的日志切面
//注意自定义切面不要与diamond-log默认的切面有重叠，否则会重复记录日志
@Aspect
@Component
public class SampleWebMonitorLog {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //TODO zhaozhao这样定义变量不支持多线程，需要用Local符号修饰
    private LocalDateTime startTime;

    @Autowired
    private IWebLogsService iWebLogsService;

    /**
     * 前置通知，在目标方法完成之后调用通知，此时不会关 心方法的输出是什么 方法调用前触发 -记录开始时间
     */
    //@Before("bean(*Controller)")
    //@Before("within(com.hoioy.diamond.common.api.BaseController+)")
    @Before("within(com.hoioy.*.api..*)")
    public void beforeAdvice(JoinPoint joinPoint) {
        startTime = LocalDateTime.now();
    }

    /**
     * 后置通知，在目标方法完成之后调用通知，此时不会关 心方法的输出是什么
     */
    //@After("bean(*Controller)")
    //@After("within(com.hoioy.diamond.common.api.BaseController+)")
    @After("within(com.hoioy.*.api..*)")
    public void afterAdvice(JoinPoint joinPoint) throws Exception {
        //TODO 此切面与diamond-log默认的WebMonitorLog中的切面有重叠，会导致重复记录日志。可以将base.log.lock设置为off以关闭diamond-log的默认日志
        Map<String, Object> param = new HashMap<>();
        param.put("startTime", startTime);
        iWebLogsService.saveLog(joinPoint, param);
    }

}
