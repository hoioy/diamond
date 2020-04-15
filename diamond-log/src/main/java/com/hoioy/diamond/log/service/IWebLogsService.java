package com.hoioy.diamond.log.service;

import com.hoioy.diamond.common.domain.DiamondDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.common.util.DiamondSecurityUtils;
import com.hoioy.diamond.common.util.WebSiteUtil;
import com.hoioy.diamond.log.annotation.OperationLogAnno;
import com.hoioy.diamond.log.dto.WebLogsDTO;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

public interface IWebLogsService<D extends DiamondDomain> extends IBaseService<WebLogsDTO, D> {

    default String saveLog(JoinPoint joinPoint, LocalDateTime startTime) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 远程客户ip地址
        String remoteClientIp = WebSiteUtil.getIpAddress(request);

        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();

        WebLogsDTO webLogsDTO = new WebLogsDTO();
        webLogsDTO.setStartTime(startTime);
        webLogsDTO.setEndTime(LocalDateTime.now());
        webLogsDTO.setLogUserName(DiamondSecurityUtils.getCurrentLogin());
        webLogsDTO.setLogMethodName(methodName);
        webLogsDTO.setLogClientIp(remoteClientIp);
        webLogsDTO.setLogServerIp(WebSiteUtil.getLocalIP());

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    if (method.getAnnotation(OperationLogAnno.class) != null) {
                        webLogsDTO.setModule(method.getAnnotation(OperationLogAnno.class).module());
                        webLogsDTO.setLogInfo(method.getAnnotation(OperationLogAnno.class).logInfo());
                        webLogsDTO.setLogClassName(method.getAnnotation(OperationLogAnno.class).logClassName());
                        webLogsDTO.setLogOperationType(method.getAnnotation(OperationLogAnno.class).logOperationType());
                        webLogsDTO.setRemark(method.getAnnotation(OperationLogAnno.class).desc());
                    } else {
                        webLogsDTO.setModule(targetName);
                        webLogsDTO.setLogInfo("执行了" + targetName + "的'" + methodName + "'操作");
                        webLogsDTO.setLogClassName(targetName);
                        if (methodName.toLowerCase().contains("list")
                                || methodName.toLowerCase().contains("page")
                                || methodName.toLowerCase().contains("tree")) {
                            webLogsDTO.setLogOperationType("query");
                        } else if (methodName.toLowerCase().contains("save")
                                || methodName.toLowerCase().contains("edit")
                                || methodName.toLowerCase().contains("add")) {
                            webLogsDTO.setLogOperationType("save");
                        } else if (methodName.toLowerCase().contains("delete")) {
                            webLogsDTO.setLogOperationType("update");
                        }
                        webLogsDTO.setRemark("qwewqe");
                    }
                }
            }
        }

        return save(webLogsDTO);
    }
}
