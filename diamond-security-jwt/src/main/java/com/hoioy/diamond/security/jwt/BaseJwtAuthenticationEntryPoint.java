package com.hoioy.diamond.security.jwt;

import com.hoioy.diamond.common.dto.ResultDTO;
import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static Logger logger = LoggerFactory.getLogger(BaseJwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        ResultDTO ResultDTO = new ResultDTO<String>(401, authException.getMessage(), "");
        logger.error("JwtAuthenticationEntryPoint", authException);
        response.getWriter().append(mapper.writeValueAsString(ResultDTO));
    }
}
