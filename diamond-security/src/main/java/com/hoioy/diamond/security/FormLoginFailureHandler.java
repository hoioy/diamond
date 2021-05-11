package com.hoioy.diamond.security;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormLoginFailureHandler implements AuthenticationFailureHandler {
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        ResultDTO<Object> resultDTO = new ResultDTO<>();
        resultDTO.setCode(401);
        resultDTO.setMessage("用户名密码错误");
        resultDTO.setErrorMessage("用户名密码错误");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String resultToken = new ObjectMapper().writeValueAsString(resultDTO);
        response.getWriter().write(resultToken);

    }
}