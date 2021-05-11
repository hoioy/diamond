package com.hoioy.diamond.security.jwt;

import com.hoioy.diamond.common.dto.ResultDTO;
import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseJwtAccessDeniedHandler implements AccessDeniedHandler {
    private static Logger logger = LoggerFactory.getLogger(BaseJwtAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        logger.debug("JwtAccessDeniedHandler,{}", accessDeniedException);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();
        ResultDTO ResultDTO = new ResultDTO<String>(401, accessDeniedException.getMessage(), "");
        logger.debug("JwtAccessDeniedHandler,return {}", ResultDTO);
        response.getWriter().append(mapper.writeValueAsString(ResultDTO));
    }
}
