package com.hoioy.diamond.log.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.common.util.WebSiteUtil;
import com.hoioy.diamond.log.annotation.OperationLogAnno;
import com.hoioy.diamond.log.dto.WebLogsDTO;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Map;

public interface IWebLogsService<D extends CommonDomain> extends IBaseService<WebLogsDTO, D> {

    default WebLogsDTO beforeSaveLog(JoinPoint joinPoint, Map<String, Object> param) throws ClassNotFoundException, SocketException, UnknownHostException {
        LocalDateTime startTime = (LocalDateTime) param.get("startTime");
        String currentLoginName = (String) param.get("currentLoginName");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            //controller中可能有其他非接口类型方法，这些方法不应该被记录日志
            return null;
        }
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
        webLogsDTO.setLogUserName(currentLoginName);
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

        return webLogsDTO;
    }

    default WebLogsDTO saveLog(JoinPoint joinPoint, Map<String, Object> param) throws SocketException, UnknownHostException, ClassNotFoundException {
        WebLogsDTO webLogsDTO = beforeSaveLog(joinPoint, param);
        if (webLogsDTO != null) {
            return create(webLogsDTO);
        }
        return null;
    }
}
